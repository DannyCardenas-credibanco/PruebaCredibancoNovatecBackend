package com.co.prueba.credibanco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.credibanco.dto.ApiResponse;
import com.co.prueba.credibanco.dto.CarritoCompraRequestDto;
import com.co.prueba.credibanco.dto.CarritoCreateRequestDto;
import com.co.prueba.credibanco.dto.CarritoCreateResponseDto;
import com.co.prueba.credibanco.dto.CarritoUpdateRequestDto;
import com.co.prueba.credibanco.dto.ProductoCarritoUpdateRequestDto;
import com.co.prueba.credibanco.service.CarritoService;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

	private final CarritoService service;
	
	@PostMapping
	public ResponseEntity<ApiResponse<CarritoCreateResponseDto>> createCarrito(@RequestBody CarritoCreateRequestDto carritoRequest){
		try {
			return ResponseEntity.ok(ApiResponse.<CarritoCreateResponseDto>builder().data(service.createCarrito(carritoRequest)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<CarritoCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<ApiResponse<CarritoCreateResponseDto>> findCarrito(@PathVariable String idUsuario) {
		try {
			return ResponseEntity.ok(ApiResponse.<CarritoCreateResponseDto>builder().data(service.findCarrito(idUsuario)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<CarritoCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PutMapping("/modificarProductos/{id}")
	public ResponseEntity<ApiResponse<CarritoCreateResponseDto>> modifyProducts(@PathVariable String id, @RequestBody CarritoUpdateRequestDto carritoRequest) {
		try {
			return ResponseEntity.ok(ApiResponse.<CarritoCreateResponseDto>builder().data(service.modifyProducts(id, carritoRequest)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<CarritoCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PutMapping("/agregar-producto/{id}")
	public ResponseEntity<ApiResponse<Integer>> agregaProducto(@PathVariable String id, @RequestBody ProductoCarritoUpdateRequestDto carritoRequest) {
		try {
			return ResponseEntity.ok(ApiResponse.<Integer>builder().data(service.addElementCarrito(id, carritoRequest)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<Integer>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PutMapping("/registrarCompra/{id}")
	public ResponseEntity<String> registrarCompra(@PathVariable String id, @RequestBody CarritoCompraRequestDto carritoCompra){
		try {
			service.registrarCompra(id, carritoCompra);
			return ResponseEntity.ok("Compra Exitosa");
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/contar-items/{id}")
	public ResponseEntity<Long> contarProductos(@PathVariable String id){
		try {
			;
			return ResponseEntity.ok(service.countCarrito(id));
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(Long.valueOf(0));
		}
	}
}
