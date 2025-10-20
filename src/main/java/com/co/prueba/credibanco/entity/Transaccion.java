package com.co.prueba.credibanco.entity;

import java.util.Date;
import java.util.UUID;

import com.co.prueba.credibanco.utils.enums.EstadoTransaccion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaccion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaccion {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
	private Carrito carrito;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta", nullable = false)
	private Tarjeta tarjeta;
	
	@Column
	@Enumerated(EnumType.STRING)
	private EstadoTransaccion estado;
	
	@Column(name = "fecha_compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCompra;

}
