package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioLoginResponseDto {

	private String id;
	
	private String nombre;
	
	private boolean isEmpleadoBanco;

}
