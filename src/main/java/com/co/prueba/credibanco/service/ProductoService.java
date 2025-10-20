package com.co.prueba.credibanco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.co.prueba.credibanco.clients.FakeStoreClient;
import com.co.prueba.credibanco.clients.PlatziClient;
import com.co.prueba.credibanco.dto.ProductoDto;
import com.co.prueba.credibanco.dto.ProductoResponseGetDto;
import com.co.prueba.credibanco.dto.clients.fakestore.ProductFakeStoreApiDto;
import com.co.prueba.credibanco.dto.clients.platzi.ProductPlatziDto;
import com.co.prueba.credibanco.entity.Producto;
import com.co.prueba.credibanco.mapper.ProductoMapper;
import com.co.prueba.credibanco.repository.ProductoRepository;
import com.co.prueba.credibanco.utils.Utils;
import com.co.prueba.credibanco.utils.enums.Proveedor;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
	
	private final ProductoRepository repository;
	
	private final ProductoMapper mapper;
	
	private final PlatziClient platziClient;
	
	private final FakeStoreClient fakeStoreClient;
	
	public Page<ProductoResponseGetDto> findByNameFilter(String filter, int page, int length) throws MarketPlaceException{
		try {
			List<ProductPlatziDto> productsPlatzi = platziClient.getallProducts();
			List<ProductFakeStoreApiDto> productsFakeStore = fakeStoreClient.getallProducts();
			List<ProductoResponseGetDto> listaProductos = new ArrayList<>();
			listaProductos.addAll(mapper.lstProductoPlatziToLstProductoResponseGet(productsPlatzi));
			listaProductos.addAll(mapper.lstProductoFakeStoreToLstProductoResponseGet(productsFakeStore));
			listaProductos.sort((producto1, producto2) -> producto1.getNombre().compareTo(producto2.getNombre()));
			List<ProductoResponseGetDto>  productsFilter = Objects.isNull(filter) || filter.isBlank() ? listaProductos : listaProductos.stream().filter(producto -> producto.getNombre().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
			return Utils.convertirListaAPagina(
					productsFilter, page, length);
		}catch(Exception e) {
			e.printStackTrace();
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}
	
	public ProductoDto getProducto(Long id, Proveedor proveedor) throws MarketPlaceException {
		try {
			if(null == id || null == proveedor) {
				return null;
			}
			Producto producto = repository.findByIdExternoAndProveedor(id, proveedor).orElse(null);
			if(Objects.isNull(producto)) {
				producto = createProducto(id, proveedor);
			}
			return mapper.productoToProductoDto(producto);
		}catch(Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}
	
	private Producto createProducto(Long id, Proveedor proveedor) {
		ProductoDto productDto;
		if(Proveedor.PLATZI.equals(proveedor)) {
			ProductPlatziDto productPlatzi= platziClient.getId(id);
			productDto =  mapper.productPlatziToProductDto(productPlatzi);
		}else {
			ProductFakeStoreApiDto productPlatzi= fakeStoreClient.getId(id);
			productDto = mapper.productFakeStoreToProductDto(productPlatzi);
		}
		Producto producto = mapper.productoDtoToProducto(productDto);
		return repository.save(producto);
	}

}
