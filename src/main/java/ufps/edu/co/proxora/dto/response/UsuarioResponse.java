package ufps.edu.co.proxora.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private UUID id;
    private String nombre;
    private String apellido;
    private String correo;
    private Boolean activo;
    private String nombreRol;
}
