package com.co.prueba.credibanco.utils.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoTarjeta {
	CREDITO("123456", "CREDITO"),
	DEBITO("456789", "DEBITO");
	
	@Getter
	private String numero;
	@Getter
	private String tipo;
	
	public static Optional<TipoTarjeta> findByCodigo(String codigo) {
	    return Arrays.stream(TipoTarjeta.values())
	                 .filter(t -> t.getTipo().equals(codigo))
	                 .findFirst();
	}

}
