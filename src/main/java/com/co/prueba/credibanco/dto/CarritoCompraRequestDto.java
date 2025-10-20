package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoCompraRequestDto {

	private String usuario;
	
	private String numeroTarjeta;
	
	private String fechaTarjeta;

}
