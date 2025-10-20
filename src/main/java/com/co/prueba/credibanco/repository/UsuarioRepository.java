package com.co.prueba.credibanco.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.prueba.credibanco.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	Optional<Usuario> findByUsername(String username);
	
	Optional<Usuario> findByUsernameIgnoreCaseAndPassword(String username, String password);
}
