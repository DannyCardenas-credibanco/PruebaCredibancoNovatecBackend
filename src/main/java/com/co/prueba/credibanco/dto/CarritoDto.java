package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;
import java.util.List;

import com.co.prueba.credibanco.utils.enums.EstadoCarrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoDto {

	private String id;
	
	private UsuarioDto usuario;
	
	private BigDecimal total;
	
	private EstadoCarrito estado;
	
	private List<ProductoDto> productos;

}
