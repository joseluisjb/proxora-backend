package ufps.edu.co.proxora.dto.response;

public record LoginResponse(
    String token,
    String rol,
    String nombreCompleto
) {}
