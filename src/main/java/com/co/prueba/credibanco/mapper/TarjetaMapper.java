package com.co.prueba.credibanco.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.co.prueba.credibanco.dto.TarjetaCreateResponseDto;
import com.co.prueba.credibanco.dto.TarjetaDto;
import com.co.prueba.credibanco.entity.Tarjeta;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
public interface TarjetaMapper {
	
	TarjetaMapper INSTANCE = Mappers.getMapper(TarjetaMapper.class);
	
	@Mapping(target = "fechaVencimiento", dateFormat = "MM/yyyy")
	TarjetaCreateResponseDto createTrajetaByEntity(Tarjeta tarjeta);
	
	TarjetaDto tarjetaToTarjetaDto(Tarjeta tarjeta);

}
