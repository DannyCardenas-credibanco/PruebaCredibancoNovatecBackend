package com.co.prueba.credibanco.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.prueba.credibanco.entity.Producto;
import com.co.prueba.credibanco.utils.enums.Proveedor;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {

	Optional<Producto> findByIdExternoAndProveedor(Long idExterno, Proveedor proveedor);
}
