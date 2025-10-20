package com.co.prueba.credibanco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.credibanco.dto.ApiResponse;
import com.co.prueba.credibanco.dto.UsuarioCreateRequestDto;
import com.co.prueba.credibanco.dto.UsuarioCreateResponseDto;
import com.co.prueba.credibanco.dto.UsuarioLoginRequestDto;
import com.co.prueba.credibanco.dto.UsuarioLoginResponseDto;
import com.co.prueba.credibanco.service.UsuarioService;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@PostMapping
	public ResponseEntity<ApiResponse<UsuarioCreateResponseDto>> crearUsuario(@RequestBody UsuarioCreateRequestDto usuarioRequest){
		try {
			return ResponseEntity.ok(ApiResponse.<UsuarioCreateResponseDto>builder().data(service.createUsuario(usuarioRequest)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<UsuarioCreateResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<UsuarioLoginResponseDto>> login(@RequestBody UsuarioLoginRequestDto loginRequest){
		try {
			return ResponseEntity.ok(ApiResponse.<UsuarioLoginResponseDto>builder().data(service.login(loginRequest)).build());
		} catch (MarketPlaceException e) {
			return ResponseEntity.badRequest().body(ApiResponse.<UsuarioLoginResponseDto>builder().mensaje(e.getMessage()).build());
		}
	}
}
