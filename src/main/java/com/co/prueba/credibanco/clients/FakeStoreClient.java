package com.co.prueba.credibanco.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.co.prueba.credibanco.dto.clients.fakestore.ProductFakeStoreApiDto;

@FeignClient(name = "fakeStoreCliente", url = "${api.fakeapi.fakestore.url}")
public interface FakeStoreClient {

	@GetMapping
	List<ProductFakeStoreApiDto> getallProducts();
	
	@GetMapping("/{id}")
	ProductFakeStoreApiDto getId(@PathVariable Long id);
}
