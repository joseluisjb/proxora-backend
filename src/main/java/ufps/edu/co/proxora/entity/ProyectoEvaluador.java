package ufps.edu.co.proxora.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proyecto_evaluadores",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_proyecto", "id_docente"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoEvaluador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_docente", nullable = false)
    private Usuario docente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignado_por", nullable = false)
    private Usuario asignadoPor;

    @Column(name = "correo_enviado", nullable = false)
    private Boolean correoEnviado = false;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private OffsetDateTime creadoEn = OffsetDateTime.now();
}
