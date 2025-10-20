package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoCreateResponseDto {

	private String id;
	
	private BigDecimal total;
	
	private List<ProductoDto> productos;

}
