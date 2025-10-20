package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TarjetaCreateResponseDto {

	private String numero;
	
	private String fechaVencimiento;
	
	private BigDecimal saldo;

}
