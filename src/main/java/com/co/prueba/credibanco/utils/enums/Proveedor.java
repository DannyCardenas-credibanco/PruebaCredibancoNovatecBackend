package com.co.prueba.credibanco.utils.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Proveedor {
	
	PLATZI,
	FAKESTORE;
	
	public static Optional<Proveedor> findByCodigo(String proveedorSearch) {
	    return Arrays.stream(Proveedor.values())
	                 .filter(t -> t.toString().equals(proveedorSearch))
	                 .findFirst();
	}

}
