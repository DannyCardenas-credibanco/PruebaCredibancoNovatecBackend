package com.co.prueba.credibanco.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.random.RandomGenerator;

import org.springframework.stereotype.Service;

import com.co.prueba.credibanco.dto.TarjetaCreateResponseDto;
import com.co.prueba.credibanco.dto.TarjetaDto;
import com.co.prueba.credibanco.dto.TarjetaUpdateRequestDto;
import com.co.prueba.credibanco.entity.Tarjeta;
import com.co.prueba.credibanco.mapper.TarjetaMapper;
import com.co.prueba.credibanco.repository.TarjetaRepository;
import com.co.prueba.credibanco.utils.enums.TipoTarjeta;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;
import com.co.prueba.credibanco.utils.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarjetaService {

	private final TarjetaRepository repository;

	private final TarjetaMapper mapper;

	public TarjetaCreateResponseDto createTarjeta(String tipo) throws MarketPlaceException {
		try {
			TipoTarjeta tipoTarjeta = TipoTarjeta.findByCodigo(tipo)
					.orElseThrow(() -> new ValidationException("Tipo de tarjeta inválido: " + tipo));
			String number = buildNumber(tipoTarjeta.getNumero());
			Date fecha = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.add(Calendar.YEAR, 3);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date nuevaFecha = cal.getTime();
			Tarjeta tarjeta = new Tarjeta();
			tarjeta.setNumero(number);
			tarjeta.setFechaVencimiento(nuevaFecha);
			tarjeta.setSaldo(BigDecimal.ZERO);
			return mapper.createTrajetaByEntity(repository.save(tarjeta));
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}

	public TarjetaCreateResponseDto agregarSaldo(String numero, TarjetaUpdateRequestDto tarjetaUpdate)
			throws MarketPlaceException {
		try {
			Tarjeta tarjeta = repository.findByNumero(numero)
					.orElseThrow(() -> new ValidationException("Numero de tarjeta inválido: " + numero));
			tarjeta.setSaldo(tarjeta.getSaldo().add(tarjetaUpdate.getSaldo()));
			if(tarjetaUpdate.getSaldo() == null || BigDecimal.ZERO.compareTo(tarjetaUpdate.getSaldo())==0) {
				throw new ValidationException("Saldo nulo o 0, digite un saldo valido");
			}
			return mapper.createTrajetaByEntity(repository.save(tarjeta));
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}

	public void restarSaldo(String id, BigDecimal value) throws MarketPlaceException {
		try {
			Tarjeta tarjeta = repository.findById(UUID.fromString(id))
					.orElseThrow(() -> new ValidationException("Numero de tarjeta inválido: " + id));
			tarjeta.setSaldo(tarjeta.getSaldo().subtract(value));
			repository.save(tarjeta);
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}

	public TarjetaDto findByNumeroAndDate(String numero, String fechaStr) throws MarketPlaceException {
        YearMonth ym;
        try {
            ym = YearMonth.parse(fechaStr, DateTimeFormatter.ofPattern("MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new MarketPlaceException("Formato de fecha inválido. Debe ser MM/YYYY");
        }

        // Consultar directamente en PostgreSQL con extract()
        Tarjeta tarjeta = repository
                .findByNumeroAndMesAnioVencimiento(numero, ym.getMonthValue(), ym.getYear())
                .orElse(null);

        return mapper.tarjetaToTarjetaDto(tarjeta);
    }


	private String buildNumber(String number) {
		String numberFinal = number.concat(buildNumberComplement());
		Tarjeta tarjetaFind = repository.findByNumero(numberFinal).orElse(null);
		return Objects.isNull(tarjetaFind) ? numberFinal : buildNumber(number);
	}

	private String buildNumberComplement() {
		RandomGenerator rng = RandomGenerator.of("L64X128MixRandom");
		long randomLong = rng.nextLong(0, 9_999_999_999L + 1);
		return complementar(String.valueOf(randomLong));
	}

	private String complementar(String numero) {
		return 10 == numero.length() ? numero : complementar("0".concat(numero));
	}
}
