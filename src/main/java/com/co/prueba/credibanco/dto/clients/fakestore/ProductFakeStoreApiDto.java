package com.co.prueba.credibanco.dto.clients.fakestore;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFakeStoreApiDto {

	private Long id;
	private String title;
	private BigDecimal price;
	private String image;
}
