package ufps.edu.co.proxora.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proyecto_lineas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoLinea {

    @EmbeddedId
    private ProyectoLineaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProyecto")
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idLineaInvestigacion")
    @JoinColumn(name = "id_linea_investigacion")
    private LineaInvestigacion lineaInvestigacion;
}
