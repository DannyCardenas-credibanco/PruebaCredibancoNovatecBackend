package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioCreateRequestDto {

	private String username;
	
	private String password;
	
	private String nombre;

}
