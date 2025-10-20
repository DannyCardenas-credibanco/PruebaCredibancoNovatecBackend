package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransaccionResponseGetDto {

	private String id;
	
	private String nombreUsuario;
	
	private String tarjeta;
	
	private BigDecimal valorCompra;
	
	private String estado;
	
	private String fechaCompra;

}
