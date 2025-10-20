package com.co.prueba.credibanco.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.co.prueba.credibanco.dto.TransaccionDto;
import com.co.prueba.credibanco.dto.TransaccionResponseGetDto;
import com.co.prueba.credibanco.entity.Transaccion;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
public interface TransaccionMapper {
	
	TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);
	
	TransaccionDto transaccionToTransaccionDto(Transaccion transaccion);
	
	Transaccion transaccionDtoToTransaccion(TransaccionDto transaccionDto);
	
	@Mapping(target = "nombreUsuario", source = "carrito.usuario.nombre")
	@Mapping(target = "tarjeta", source = "transaccion.tarjeta.numero", qualifiedByName = "enmascararTarjeta")
	@Mapping(target = "fechaCompra", source = "fechaCompra", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	@Mapping(target = "valorCompra", source = "carrito.total")
	TransaccionResponseGetDto transaccionToTransaccionResponseGetDto(Transaccion transaccion);
	
	default Page<TransaccionResponseGetDto> pageTransaccionToPageTransaccionResponseGetDto(Page<Transaccion> page) {
        List<TransaccionResponseGetDto> dtos = page.getContent()
                                        .stream()
                                        .map(this::transaccionToTransaccionResponseGetDto)
                                        .toList();
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }
	
	@Named("enmascararTarjeta")
	default String enmascararTarjeta(String numero) {
		return "************".concat(numero.substring(numero.length()-4));
	}

}
