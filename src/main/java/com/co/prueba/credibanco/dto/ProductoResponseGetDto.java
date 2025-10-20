package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoResponseGetDto {

	private Long idExterno;
	
	private String nombre;
	
	private BigDecimal precio;
	
	private String proveedor;
	
	private List<String> images;
	

}
