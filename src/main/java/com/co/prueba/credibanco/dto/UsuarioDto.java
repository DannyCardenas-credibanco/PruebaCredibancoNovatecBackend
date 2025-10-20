package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDto {

	private String id;
	
	private String username;
	
	private String password;
	
	private String nombre;
	
	private boolean isEmpleadoBanco;

}
