package ufps.edu.co.proxora.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import ufps.edu.co.proxora.entity.Evaluacion;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.Usuario;

@Service
@RequiredArgsConstructor
public class CorreoService {

    private static final Logger log = LoggerFactory.getLogger(CorreoService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    public void notificarEvaluadorAsignado(Usuario docente, Proyecto proyecto) {
        String asunto = "Proxora - Has sido asignado como evaluador";
        String cuerpo = buildEvaluadorAsignadoHtml(docente, proyecto);
        enviar(docente.getCorreo(), asunto, cuerpo);
    }

    public void notificarCalificacion(List<ProyectoIntegrante> integrantes, Proyecto proyecto, Evaluacion evaluacion) {
        String asunto = "Proxora - Tu proyecto ha sido calificado";
        String cuerpo = buildCalificacionHtml(proyecto, evaluacion);
        integrantes.forEach(i -> enviar(i.getUsuario().getCorreo(), asunto, cuerpo));
    }

    private void enviar(String destinatario, String asunto, String cuerpo) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, "UTF-8");
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, true);
            mailSender.send(mensaje);
        } catch (Exception e) {
            log.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
        }
    }

    private String buildEvaluadorAsignadoHtml(Usuario docente, Proyecto proyecto) {
        return """
                <html><body style="font-family:Arial,sans-serif;color:#222;max-width:600px;margin:auto">
                  <h2 style="color:#4f46e5">Proxora</h2>
                  <p>Hola, <strong>%s %s</strong>.</p>
                  <p>Has sido asignado como <strong>evaluador</strong> del siguiente proyecto:</p>
                  <div style="background:#f5f5f5;border-left:4px solid #4f46e5;padding:12px 16px;margin:16px 0">
                    <strong>%s</strong>
                  </div>
                  <p>Ingresa a la plataforma para revisar el proyecto y registrar tu calificación.</p>
                  <br>
                  <p style="color:#888;font-size:12px">Este es un mensaje automático, no respondas a este correo.</p>
                </body></html>
                """.formatted(docente.getNombre(), docente.getApellido(), proyecto.getTitulo());
    }

    private String buildCalificacionHtml(Proyecto proyecto, Evaluacion evaluacion) {
        return """
                <html><body style="font-family:Arial,sans-serif;color:#222;max-width:600px;margin:auto">
                  <h2 style="color:#4f46e5">Proxora</h2>
                  <p>El proyecto <strong>%s</strong> ha recibido una calificación.</p>
                  <div style="background:#f5f5f5;border-left:4px solid #4f46e5;padding:12px 16px;margin:16px 0">
                    <p><strong>Calificación:</strong> %s / 5.0</p>
                    <p><strong>Retroalimentación:</strong></p>
                    <p>%s</p>
                  </div>
                  <p>Ingresa a la plataforma para ver el detalle completo.</p>
                  <br>
                  <p style="color:#888;font-size:12px">Este es un mensaje automático, no respondas a este correo.</p>
                </body></html>
                """.formatted(proyecto.getTitulo(), evaluacion.getCalificacion().toPlainString(), evaluacion.getComentario());
    }
}
