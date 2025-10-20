package com.co.prueba.credibanco.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.co.prueba.credibanco.utils.enums.Proveedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Proveedor proveedor;
	
	@Column(name = "id_externo")
	private Long idExterno;
	
	@Column
	private String nombre;
	
	@Column
	private BigDecimal precio;
	

}
