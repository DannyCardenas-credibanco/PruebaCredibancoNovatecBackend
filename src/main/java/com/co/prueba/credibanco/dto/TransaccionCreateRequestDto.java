package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransaccionCreateRequestDto {

	private String idCarrito;
	
	private String numeroTarjeta;
	
	private String fechaTarjeta;
}
