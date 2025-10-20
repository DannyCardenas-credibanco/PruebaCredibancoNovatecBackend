package com.co.prueba.credibanco.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarjeta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tarjeta {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column
	private String numero;
	
	@Column(name = "fecha_vencimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaVencimiento;
	
	@Column
	private BigDecimal saldo;

}
