package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;

import com.co.prueba.credibanco.utils.enums.Proveedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDto {

	private String id;
	
	private Proveedor proveedor;
	
	private Long idExterno;
	
	private String nombre;
	
	private BigDecimal precio;
	

}
