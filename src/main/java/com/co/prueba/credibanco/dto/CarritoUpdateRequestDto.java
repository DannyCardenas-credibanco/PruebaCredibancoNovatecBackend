package com.co.prueba.credibanco.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoUpdateRequestDto {

	private List<ProductoCarritoUpdateRequestDto> productos;

}
