package ufps.edu.co.proxora.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProyectoDirectorId implements Serializable {

    @Column(name = "id_proyecto")
    private UUID idProyecto;

    @Column(name = "id_docente")
    private UUID idDocente;
}
