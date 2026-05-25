package ufps.edu.co.proxora.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_documento")
public class TipoDocumento {
    @Id
    private Short id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}
