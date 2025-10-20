package com.co.prueba.credibanco.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.prueba.credibanco.entity.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

}
