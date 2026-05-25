package ufps.edu.co.proxora.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.entity.Role;
import ufps.edu.co.proxora.entity.Usuario;
import ufps.edu.co.proxora.repository.RoleRepository;
import ufps.edu.co.proxora.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.correo}")
    private String adminCorreo;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.findByCorreo(adminCorreo).isEmpty()) {
            Role rolAdmin = roleRepository.findById((short) 1)
                .orElseThrow(() -> new RuntimeException("Rol admin no encontrado"));

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellido("Proxora");
            admin.setCorreo(adminCorreo);
            admin.setContrasenaHash(passwordEncoder.encode(adminPassword));
            admin.setRol(rolAdmin);
            admin.setActivo(true);

            usuarioRepository.save(admin);
            System.out.println("✅ Admin creado: " + adminCorreo);
        }
    }
}
