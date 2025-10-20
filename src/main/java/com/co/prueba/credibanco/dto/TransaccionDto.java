package com.co.prueba.credibanco.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.co.prueba.credibanco.utils.enums.EstadoTransaccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransaccionDto {

	private String id;
	
	private CarritoDto carrito;
	
	private TarjetaDto tarjeta;
	
	private BigDecimal valorCompra;
	
	private EstadoTransaccion estado;
	
	private Date fechaCompra;

}
