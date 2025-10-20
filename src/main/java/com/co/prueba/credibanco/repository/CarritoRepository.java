package com.co.prueba.credibanco.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.prueba.credibanco.entity.Carrito;
import com.co.prueba.credibanco.utils.enums.EstadoCarrito;

public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
	Optional<Carrito> findByUsuarioIdAndEstado(UUID idUsuario, EstadoCarrito estado);
}
