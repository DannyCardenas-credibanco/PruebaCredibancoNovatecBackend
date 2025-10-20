package com.co.prueba.credibanco.dto;

import com.co.prueba.credibanco.utils.enums.Proveedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoCreateRequestDto {

	private String usuario;
	
	private Proveedor proveedor;
	
	private Long idProducto;

}
