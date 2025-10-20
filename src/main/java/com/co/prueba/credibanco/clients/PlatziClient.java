package com.co.prueba.credibanco.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.co.prueba.credibanco.dto.clients.platzi.ProductPlatziDto;

@FeignClient(name = "platziCliente", url = "${api.fakeapi.platzi.url}")
public interface PlatziClient {

	@GetMapping
	List<ProductPlatziDto> getallProducts();
	
	@GetMapping("/{id}")
	ProductPlatziDto getId(@PathVariable Long id);
}
