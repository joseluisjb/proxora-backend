package ufps.edu.co.proxora.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estados_proyecto")
public class EstadoProyecto {
    @Id
    private Short id;

    @Column(nullable = false, unique = true, length = 30)
    private String nombre;
}
