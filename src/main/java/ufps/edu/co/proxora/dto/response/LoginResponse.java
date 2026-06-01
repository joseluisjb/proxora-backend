package ufps.edu.co.proxora.dto.response;

import java.util.UUID;

public record LoginResponse(
    String token,
    String rol,
    String nombreCompleto,
    UUID id
) {}
