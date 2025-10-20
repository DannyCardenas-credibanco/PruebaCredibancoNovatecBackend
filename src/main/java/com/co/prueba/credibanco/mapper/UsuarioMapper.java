package com.co.prueba.credibanco.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.co.prueba.credibanco.dto.UsuarioCreateRequestDto;
import com.co.prueba.credibanco.dto.UsuarioCreateResponseDto;
import com.co.prueba.credibanco.dto.UsuarioDto;
import com.co.prueba.credibanco.dto.UsuarioLoginResponseDto;
import com.co.prueba.credibanco.entity.Usuario;

@Mapper(
	    componentModel = "spring",
	    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
	)
public interface UsuarioMapper {
	
	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
	
	UsuarioDto usuarioToUsuarioDto(Usuario usuario);
	
	Usuario usuarioCreateRequestDtoToUsuario(UsuarioCreateRequestDto usuarioCreate);
	
	UsuarioCreateResponseDto usuarioToUsuarioCreateResponse(Usuario usuario);
	
	UsuarioLoginResponseDto usuarioToUsuarioLoginResponseDto(Usuario usuario);

}
