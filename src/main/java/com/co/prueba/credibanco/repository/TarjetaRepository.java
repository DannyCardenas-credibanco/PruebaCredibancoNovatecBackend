package com.co.prueba.credibanco.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.co.prueba.credibanco.entity.Tarjeta;

public interface TarjetaRepository extends JpaRepository<Tarjeta, UUID> {

	Optional<Tarjeta> findByNumero (String numero);
	
	@Query(value = """
	        SELECT * FROM tarjeta t
	        WHERE t.numero = :numero
	          AND extract(month from t.fecha_vencimiento) = :mes
	          AND extract(year  from t.fecha_vencimiento) = :anio
	        """, nativeQuery = true)
	    Optional<Tarjeta> findByNumeroAndMesAnioVencimiento(
	            @Param("numero") String numero,
	            @Param("mes") int mes,
	            @Param("anio") int anio);
}
