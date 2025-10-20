package com.co.prueba.credibanco.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.co.prueba.credibanco.dto.UsuarioCreateRequestDto;
import com.co.prueba.credibanco.dto.UsuarioCreateResponseDto;
import com.co.prueba.credibanco.dto.UsuarioDto;
import com.co.prueba.credibanco.dto.UsuarioLoginRequestDto;
import com.co.prueba.credibanco.dto.UsuarioLoginResponseDto;
import com.co.prueba.credibanco.entity.Usuario;
import com.co.prueba.credibanco.mapper.UsuarioMapper;
import com.co.prueba.credibanco.repository.UsuarioRepository;
import com.co.prueba.credibanco.utils.Utils;
import com.co.prueba.credibanco.utils.exception.MarketPlaceException;
import com.co.prueba.credibanco.utils.exception.ValidationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;

	private final UsuarioMapper mapper;

	public UsuarioCreateResponseDto createUsuario(UsuarioCreateRequestDto usuarioRequest) throws MarketPlaceException {
		try {
			if (Utils.validateContentStr(usuarioRequest.getUsername())) {
				throw new ValidationException("El username es obligatorio");
			}
			if (Utils.validateContentStr(usuarioRequest.getPassword())) {
				throw new ValidationException("El password es obligatorio");
			}
			if (Utils.validateContentStr(usuarioRequest.getNombre())) {
				throw new ValidationException("El nombre es obligatorio");
			}
			Usuario usuario = repository.findByUsername(usuarioRequest.getUsername()).orElse(null);
			if(Objects.nonNull(usuario)) {
				throw new ValidationException("El username ya existe");
			}
			usuario = mapper.usuarioCreateRequestDtoToUsuario(usuarioRequest);
			return mapper.usuarioToUsuarioCreateResponse(repository.save(usuario));
		} catch (ValidationException e) {
			throw new MarketPlaceException("Error de validacion: ".concat(e.getMessage()));
		} catch (Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
	}
	
	public UsuarioLoginResponseDto login(UsuarioLoginRequestDto userLoginRequest) throws MarketPlaceException {
		try {
		if(Utils.validateContentStr(userLoginRequest.getUsername())) {
			throw new ValidationException("El username es obligatorio.");
		}
		if(Utils.validateContentStr(userLoginRequest.getPassword())) {
			throw new ValidationException("El password es obligatorio.");
		}
		Usuario usuario = repository.findByUsernameIgnoreCaseAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword()).orElse(null);
		return mapper.usuarioToUsuarioLoginResponseDto(usuario);
		}catch(ValidationException e) {
			throw new MarketPlaceException(e.getMessage());
		} catch (Exception e) {
			throw new MarketPlaceException("Error general: ".concat(e.getMessage()));
		}
		
	}
	
	public UsuarioDto getById(String id) {
		return mapper.usuarioToUsuarioDto(repository.findById(UUID.fromString(id)).orElse(null));
	}

}
