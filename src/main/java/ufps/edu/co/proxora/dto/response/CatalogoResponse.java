package ufps.edu.co.proxora.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatalogoResponse {
    private Short id;
    private String nombre;
    private String descripcion;
}
