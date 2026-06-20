package ufps.edu.co.proxora.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.entity.Rol;
import ufps.edu.co.proxora.repository.RolRepository;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public String obtenerNombreRol(Short id){
        return rolRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado")).getNombre();
    }

    public Rol obtenerRolPorNombre(String nombre){
        return rolRepository.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }
}
