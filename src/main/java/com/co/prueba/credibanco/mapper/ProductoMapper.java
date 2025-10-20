package com.co.prueba.credibanco.mapper;

import java.util.List;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.co.prueba.credibanco.dto.ProductoDto;
import com.co.prueba.credibanco.dto.ProductoResponseGetDto;
import com.co.prueba.credibanco.dto.clients.fakestore.ProductFakeStoreApiDto;
import com.co.prueba.credibanco.dto.clients.platzi.ProductPlatziDto;
import com.co.prueba.credibanco.entity.Producto;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	    imports = {com.co.prueba.credibanco.utils.enums.Proveedor.class}
	)
public interface ProductoMapper {
	
	
	ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);
	
	ProductoDto productoToProductoDto(Producto producto);
	
	Producto productoDtoToProducto(ProductoDto producto);
	
	@Mapping(target = "idExterno", source = "id")
	@Mapping(target = "nombre", source = "title")
	@Mapping(target = "precio", source = "price")
	@Mapping(target = "proveedor", expression = "java(Proveedor.PLATZI.toString())")
	ProductoResponseGetDto productoPlatziToProductoResponseGet(ProductPlatziDto productoPlatzi);
	
	@Mapping(target = "idExterno", source = "id")
	@Mapping(target = "nombre", source = "title")
	@Mapping(target = "precio", source = "price")
	@Mapping(target = "images", source = "image", qualifiedByName = "strToLst")
	@Mapping(target = "proveedor", expression = "java(Proveedor.FAKESTORE.toString())")
	ProductoResponseGetDto productoFakeStoreToProductoResponseGet(ProductFakeStoreApiDto productFakeStoreapiDto);
	
	@Mapping(target = "idExterno", source = "id")
	@Mapping(target = "nombre", source = "title")
	@Mapping(target = "precio", source = "price")
	@Mapping(target = "proveedor", expression = "java(Proveedor.PLATZI)")
	@Mapping(target = "id", ignore = true)
	ProductoDto productPlatziToProductDto(ProductPlatziDto productoPlatzi);
	
	@Mapping(target = "idExterno", source = "id")
	@Mapping(target = "nombre", source = "title")
	@Mapping(target = "precio", source = "price")
	@Mapping(target = "proveedor", expression = "java(Proveedor.FAKESTORE)")
	@Mapping(target = "id", ignore = true)
	ProductoDto productFakeStoreToProductDto(ProductFakeStoreApiDto productoFakeStore);
	
	List<ProductoResponseGetDto> lstProductoPlatziToLstProductoResponseGet(List<ProductPlatziDto> productoPlatzi);
	
	List<ProductoResponseGetDto> lstProductoFakeStoreToLstProductoResponseGet(List<ProductFakeStoreApiDto> productFakeStoreapiDto);
	
	@Named("strToLst")
	default List<String> strToLst(String value){
		if(Objects.isNull(value) || value.isBlank())return List.of();
		return List.of(value);
	}

}
