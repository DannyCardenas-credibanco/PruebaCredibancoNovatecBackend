package com.co.prueba.credibanco.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.co.prueba.credibanco.dto.CarritoCompraRequestDto;
import com.co.prueba.credibanco.dto.CarritoCreateRequestDto;
import com.co.prueba.credibanco.dto.CarritoCreateResponseDto;
import com.co.prueba.credibanco.dto.CarritoDto;
import com.co.prueba.credibanco.dto.CarritoUpdateRequestDto;
import com.co.prueba.credibanco.dto.ProductoCarritoUpdateRequestDto;
import com.co.prueba.credibanco.dto.ProductoDto;
import com.co.prueba.credibanco.dto.TransaccionDto;
import com.co.prueba.credibanco.dto.UsuarioDto;
import com.co.prueba.credibanco.entity.Carrito;
import com.co.prueba.credibanco.mapper.CarritoMapper;
import com.co.prueba.credibanco.repository.CarritoRepository;
import com.co.prueba.credibanco.utils.enums.EstadoCarrito;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;
import com.co.prueba.credibanco.utils.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {

	private final CarritoRepository repository;

	private final CarritoMapper mapper;

	private final ProductoService productoService;

	private final UsuarioService usuarioService;
	
	private final TransaccionService transaccionService;

	public CarritoCreateResponseDto createCarrito(CarritoCreateRequestDto carritoRequest) throws MarketPlaceException {
		try {
			UsuarioDto usuario = usuarioService.getById(carritoRequest.getUsuario());
			ProductoDto producto = productoService.getProducto(carritoRequest.getIdProducto(),
					carritoRequest.getProveedor());
			CarritoDto carritoDto = new CarritoDto();
			carritoDto.setUsuario(usuario);
			carritoDto.setEstado(EstadoCarrito.PENDIENTE);
			if(Objects.nonNull(producto)) {
				carritoDto.setProductos(List.of(producto));
				carritoDto.setTotal(totalPrecio(carritoDto.getProductos()));
			}else {
				carritoDto.setProductos(new ArrayList<>());
				carritoDto.setTotal(BigDecimal.ZERO);
			}
			Carrito carrito = mapper.carritoDtoToCarrito(carritoDto);
			return mapper.carritoToCarritoCreateResponseDto(repository.save(carrito));
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	public CarritoCreateResponseDto findCarrito(String idUsuario) throws MarketPlaceException {
		try{
			Carrito carrito = repository.findByUsuarioIdAndEstado(UUID.fromString(idUsuario), EstadoCarrito.PENDIENTE).orElse(null);
			if(Objects.isNull(carrito)) {
				CarritoCreateRequestDto request = new CarritoCreateRequestDto();
				request.setUsuario(idUsuario);
				return createCarrito(request);
			}else {
				return mapper.carritoToCarritoCreateResponseDto(carrito);
			}
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	public Integer addElementCarrito(String id,ProductoCarritoUpdateRequestDto productoRequest) throws MarketPlaceException {
		try {
			Carrito carrito = repository.findById(UUID.fromString(id)).orElse(null);
			if (Objects.isNull(carrito)) {
				throw new ValidationException("Carrito no encontrado: " + id);
			}
			ProductoDto productDto = productoService.getProducto(productoRequest.getIdExterno(),
					productoRequest.getProveedor());
			CarritoDto carritoDto = mapper.carritoToCarritoDto(carrito);
			carritoDto.getProductos().add(productDto);
			carritoDto.setTotal(totalPrecio(carritoDto.getProductos()));
			carrito = repository.save(mapper.carritoDtoToCarrito(carritoDto));
			return carrito.getProductos().size();
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}

	public CarritoCreateResponseDto modifyProducts(String id, CarritoUpdateRequestDto carritoRequest)
			throws MarketPlaceException {
		try {
			
			Carrito carrito = repository.findById(UUID.fromString(id)).orElse(null);
			if (Objects.isNull(carrito)) {
				throw new ValidationException("Carrito no encontrado: " + id);
			}
			if (Objects.isNull(carritoRequest.getProductos()) || carritoRequest.getProductos().isEmpty()) {
				carrito.setProductos(new ArrayList<>());
				carrito.setTotal(BigDecimal.ZERO);
			} else {
				CarritoDto carritoDto = mapper.carritoToCarritoDto(carrito);
				List<ProductoDto> products = carritoRequest.getProductos().stream().map(productoRequest -> {
					try {
						return productoService.getProducto(productoRequest.getIdExterno(),
								productoRequest.getProveedor());
					} catch (MarketPlaceException e) {
						// TODO Auto-generated catch block
						return null;
					}
				}).collect(Collectors.toList());
				if (products.contains(null)) {
					throw new MarketPlaceException("Error agregando producto.");
				}
				carritoDto.setProductos(products);
				carritoDto.setTotal(totalPrecio(products));
				carrito = mapper.carritoDtoToCarrito(carritoDto);
			}
			return mapper.carritoToCarritoCreateResponseDto(repository.save(carrito));
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	public Long countCarrito(String idCarrito) throws MarketPlaceException {
		try{
			Carrito carrito = repository.findById(UUID.fromString(idCarrito)).orElse(null);
			if (Objects.isNull(carrito)) {
				throw new ValidationException("Carrito no encontrado: " + idCarrito);
			}
			return Long.valueOf(carrito.getProductos().size()) ;
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
		
	}
	
	@Transactional
	public boolean registrarCompra(String id, CarritoCompraRequestDto carritoCompra) throws MarketPlaceException {
		Carrito carrito = null;
		try {
			carrito = repository.findById(UUID.fromString(id)).orElse(null);
			if (Objects.isNull(carrito)) {
				throw new ValidationException("Carrito no encontrado: " + id);
			}
			if(transaccionService.createTransaccion(carritoCompra, mapper.carritoToCarritoDto(carrito))) {
				carrito.setEstado(EstadoCarrito.FINALIZADO);
				repository.save(carrito);
			}
			return true;
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (MarketPlaceException e) {
			if(Objects.nonNull(carrito)) {
				carrito.setEstado(EstadoCarrito.FALLIDO);
				repository.save(carrito);
			}
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}
	
	@Transactional
	public boolean anularCompra(String idTransaccion) throws MarketPlaceException {
		try {
			TransaccionDto transaccion = transaccionService.anularTransaccion(idTransaccion);
			Carrito carrito = repository.findById(UUID.fromString(transaccion.getCarrito().getId())).orElse(null);
			if (Objects.isNull(carrito)) {
				throw new ValidationException("Carrito no encontrado: " + transaccion.getCarrito().getId());
			}
			carrito.setEstado(EstadoCarrito.FALLIDO);
			repository.save(carrito);
			return true;
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (MarketPlaceException e) {
			throw new MarketPlaceException(e.getMessage());
		}  catch (Exception e) {
			throw new MarketPlaceException("Error general"+e.getMessage());
		}
	}

	private BigDecimal totalPrecio(List<ProductoDto> productos) {
		return productos.stream().map(ProductoDto::getPrecio).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
