package com.co.prueba.credibanco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioLoginRequestDto {

	private String username;
	
	private String password;

}
