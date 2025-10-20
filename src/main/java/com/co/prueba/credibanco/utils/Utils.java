package com.co.prueba.credibanco.utils;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public final class Utils {
	public static <ProductoResponseGetDto> Page<ProductoResponseGetDto> convertirListaAPagina(List<ProductoResponseGetDto> lista, int pagina, int tamanio) {
		 if (lista == null || lista.isEmpty()) {
	            return Page.empty(PageRequest.of(pagina, tamanio));
	        }
		int start = (int) PageRequest.of(pagina, tamanio).getOffset();
        int end = Math.min((start + tamanio), lista.size());
        List<ProductoResponseGetDto> subLista = lista.subList(start, end);
        return new PageImpl<>(subLista, PageRequest.of(pagina, tamanio), lista.size());
    }
	
	public static boolean validateContentStr(String value) {
		return Objects.isNull(value) || value.isBlank();
	}
}
