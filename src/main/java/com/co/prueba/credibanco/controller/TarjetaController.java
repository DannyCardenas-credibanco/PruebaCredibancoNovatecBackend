package com.co.prueba.credibanco.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.credibanco.dto.ApiResponse;
import com.co.prueba.credibanco.dto.TarjetaCreateRequestDto;
import com.co.prueba.credibanco.dto.TarjetaCreateResponseDto;
import com.co.prueba.credibanco.dto.TarjetaUpdateRequestDto;
import com.co.prueba.credibanco.service.TarjetaService;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/tarjeta")
@RequiredArgsConstructor
public class TarjetaController {

	private final TarjetaService service;
	
	@PostMapping
	public ResponseEntity<ApiResponse<TarjetaCreateResponseDto>> createTarjeta(@RequestBody TarjetaCreateRequestDto request) {
		try {
			
			return ResponseEntity.ok(ApiResponse.<TarjetaCreateResponseDto>builder().data(service.createTarjeta(request.getTipo())).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<TarjetaCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PutMapping("/agregarSaldo/{numero}")
	public ResponseEntity<ApiResponse<TarjetaCreateResponseDto>> agregarSaldo(@PathVariable String numero, @RequestBody TarjetaUpdateRequestDto tarjetaUpdate){
		try {
			return ResponseEntity.ok(ApiResponse.<TarjetaCreateResponseDto>builder().data(service.agregarSaldo(numero, tarjetaUpdate)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<TarjetaCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
}
