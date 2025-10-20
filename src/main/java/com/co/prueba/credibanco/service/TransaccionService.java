package com.co.prueba.credibanco.service;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.co.prueba.credibanco.dto.CarritoCompraRequestDto;
import com.co.prueba.credibanco.dto.CarritoDto;
import com.co.prueba.credibanco.dto.TarjetaDto;
import com.co.prueba.credibanco.dto.TarjetaUpdateRequestDto;
import com.co.prueba.credibanco.dto.TransaccionDto;
import com.co.prueba.credibanco.dto.TransaccionResponseGetDto;
import com.co.prueba.credibanco.entity.Transaccion;
import com.co.prueba.credibanco.mapper.TransaccionMapper;
import com.co.prueba.credibanco.repository.TransaccionRepository;
import com.co.prueba.credibanco.utils.enums.EstadoTransaccion;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;
import com.co.prueba.credibanco.utils.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransaccionService {
	
	private final TransaccionRepository repository;
	
	private final TransaccionMapper mapper;

	private final TarjetaService tarjetaService;
	
	public boolean createTransaccion(CarritoCompraRequestDto carritoCompra, CarritoDto carrito) throws MarketPlaceException {
		try {
			TarjetaDto tarjeta = tarjetaService.findByNumeroAndDate(carritoCompra.getNumeroTarjeta(), carritoCompra.getFechaTarjeta());
			if(Objects.isNull(tarjeta)) {
				throw new ValidationException("La tarjeta no existe.");
			}
			TransaccionDto transaccionDto = new TransaccionDto();
			transaccionDto.setCarrito(carrito);
			transaccionDto.setTarjeta(tarjeta);
			transaccionDto.setValorCompra(carrito.getTotal());
			transaccionDto.setFechaCompra(new Date());
			if(tarjeta.getSaldo().compareTo(carrito.getTotal())>=0 ) {
				tarjetaService.restarSaldo(tarjeta.getId(), carrito.getTotal());
				transaccionDto.setEstado(EstadoTransaccion.EXITOSA);
			}else {
				transaccionDto.setEstado(EstadoTransaccion.RECHAZADA);
			}
			repository.save(mapper.transaccionDtoToTransaccion(transaccionDto));
			if(EstadoTransaccion.EXITOSA.equals(transaccionDto.getEstado())) {
				return true;
			}else if(EstadoTransaccion.RECHAZADA.equals(transaccionDto.getEstado())) {
				throw new ValidationException("Fondos insuficientes.");
			}else {
				throw new ValidationException("error general en el registro de compra.");
			}
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	public TransaccionDto anularTransaccion(String id) throws MarketPlaceException {
		try {
			Transaccion transaccion = repository.findById(UUID.fromString(id)).orElse(null);
			if(Objects.isNull(transaccion)) {
				throw new ValidationException("No existe la transaccion con el id.");
			}
			long horas = Duration.between(transaccion.getFechaCompra().toInstant(), new Date().toInstant()).toHours();
	        if( horas <= 24) {
	        	transaccion.setEstado(EstadoTransaccion.ANULADA);
	        }
	        TarjetaUpdateRequestDto tarjetarequest = new TarjetaUpdateRequestDto();
	        tarjetarequest.setSaldo(transaccion.getCarrito().getTotal());
	        tarjetaService.agregarSaldo(transaccion.getTarjeta().getNumero(), tarjetarequest);
	        return mapper.transaccionToTransaccionDto(repository.save(transaccion));
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}

	public Page<TransaccionResponseGetDto> listarTransacicones(int page, int size) throws MarketPlaceException{
		try {
		Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCompra").descending());
		Page<Transaccion> pagina = repository.findAll(pageable);
		return mapper.pageTransaccionToPageTransaccionResponseGetDto(pagina);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	
}
