package com.co.prueba.credibanco.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.co.prueba.credibanco.utils.enums.EstadoCarrito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Carrito {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
	@Column
	private BigDecimal total;
	
	@Column
	@Enumerated(EnumType.STRING)
	private EstadoCarrito estado;
	
	@ManyToMany
    @JoinTable(
        name = "carrito_producto",
        joinColumns = @JoinColumn(name = "id_carrito"),
        inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> productos;

}
