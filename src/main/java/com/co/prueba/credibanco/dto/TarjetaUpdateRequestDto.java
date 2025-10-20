package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaUpdateRequestDto {

	private BigDecimal saldo;
}
