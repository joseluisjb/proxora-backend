package ufps.edu.co.proxora.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "niveles_visibilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NivelVisibilidad {

    @Id
    private Short id;

    @Column(nullable = false, unique = true, length = 30)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
