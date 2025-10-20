package com.co.prueba.credibanco.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.credibanco.dto.ApiResponse;
import com.co.prueba.credibanco.dto.ProductoResponseGetDto;
import com.co.prueba.credibanco.service.ProductoService;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoService service;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<ProductoResponseGetDto>>> findByName(
			@RequestParam(name="filtro", defaultValue = "") String filtro,
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="length", defaultValue = "10") int length
			){
		try {
			return ResponseEntity.ok(ApiResponse.<Page<ProductoResponseGetDto>>builder().data(service.findByNameFilter(filtro, page, length)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<Page<ProductoResponseGetDto>>builder().mensaje(e.getMessage()).build());
		}
	}
}
