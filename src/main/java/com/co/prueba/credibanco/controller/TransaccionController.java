package com.co.prueba.credibanco.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.credibanco.dto.ApiResponse;
import com.co.prueba.credibanco.dto.TransaccionResponseGetDto;
import com.co.prueba.credibanco.service.CarritoService;
import com.co.prueba.credibanco.service.TransaccionService;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/transaccion")
@RequiredArgsConstructor
public class TransaccionController {
	
	private final TransaccionService service;
	
	private final CarritoService carritoService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<TransaccionResponseGetDto>>> getTransacciones(
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="length", defaultValue = "10") int length
	 ){
		try {
			return ResponseEntity.ok(ApiResponse.<Page<TransaccionResponseGetDto>>builder().data(service.listarTransacicones(page, length)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<Page<TransaccionResponseGetDto>>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PutMapping("/anular/{id}")
	public ResponseEntity<String> anularTransaccion(@PathVariable String id){
		try {
			carritoService.anularCompra(id);
			return ResponseEntity.ok("Anulación Exitosa");
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
