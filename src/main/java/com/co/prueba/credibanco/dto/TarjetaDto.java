package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TarjetaDto {

	private String id;
	
	private String numero;
	
	private Date fechaVencimiento;
	
	private BigDecimal saldo;

}
