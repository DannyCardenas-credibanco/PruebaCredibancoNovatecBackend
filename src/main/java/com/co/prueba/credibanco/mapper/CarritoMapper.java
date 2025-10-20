package com.co.prueba.credibanco.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.co.prueba.credibanco.dto.CarritoCreateResponseDto;
import com.co.prueba.credibanco.dto.CarritoDto;
import com.co.prueba.credibanco.entity.Carrito;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
public interface CarritoMapper {
	
	CarritoMapper INSTANCE = Mappers.getMapper(CarritoMapper.class);
	
	CarritoDto carritoToCarritoDto(Carrito carrito);
	
	Carrito carritoDtoToCarrito(CarritoDto carritoDto);
	
	CarritoCreateResponseDto carritoToCarritoCreateResponseDto(Carrito carrito);

}
