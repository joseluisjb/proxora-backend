package ufps.edu.co.proxora.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import ufps.edu.co.proxora.entity.Evaluacion;
import ufps.edu.co.proxora.entity.Proyecto;
import ufps.edu.co.proxora.entity.ProyectoIntegrante;
import ufps.edu.co.proxora.entity.Usuario;

@Service
@RequiredArgsConstructor
public class CorreoService {

    private static final Logger log = LoggerFactory.getLogger(CorreoService.class);

    private final SesV2Client sesV2Client;

    @Value("${app.mail.remitente}")
    private String remitente;

    public void notificarEvaluadorAsignado(Usuario docente, Proyecto proyecto) {
        enviar(docente.getCorreo(),
                "Proxora - Has sido asignado como evaluador",
                buildEvaluadorAsignadoHtml(docente, proyecto));
    }

    public void notificarCalificacion(List<ProyectoIntegrante> integrantes, Proyecto proyecto, Evaluacion evaluacion) {
        String cuerpo = buildCalificacionHtml(proyecto, evaluacion);
        integrantes.forEach(i -> enviar(i.getUsuario().getCorreo(),
                "Proxora - Tu proyecto ha sido calificado", cuerpo));
    }

    public void notificarRecuperacionContrasena(String destinatario, String nombre, String linkRestablecimiento) {
        enviar(destinatario,
                "Proxora - Recuperación de contraseña",
                buildRecuperacionHtml(nombre, linkRestablecimiento));
    }

    private void enviar(String destinatario, String asunto, String cuerpo) {
        try {
            SendEmailRequest request = SendEmailRequest.builder()
                    .fromEmailAddress(remitente)
                    .destination(Destination.builder().toAddresses(destinatario).build())
                    .content(EmailContent.builder()
                            .simple(Message.builder()
                                    .subject(Content.builder().data(asunto).charset("UTF-8").build())
                                    .body(Body.builder()
                                            .html(Content.builder().data(cuerpo).charset("UTF-8").build())
                                            .build())
                                    .build())
                            .build())
                    .build();
            sesV2Client.sendEmail(request);
        } catch (Exception e) {
            log.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
        }
    }

    private String buildRecuperacionHtml(String nombre, String link) {
        return """
                <html><body style="font-family:Arial,sans-serif;color:#222;max-width:600px;margin:auto">
                  <h2 style="color:#4f46e5">Proxora</h2>
                  <p>Hola, <strong>%s</strong>.</p>
                  <p>Recibimos una solicitud para restablecer tu contraseña. Haz clic en el botón para continuar:</p>
                  <div style="text-align:center;margin:24px 0">
                    <a href="%s" style="background:#4f46e5;color:#fff;padding:12px 24px;border-radius:6px;text-decoration:none;font-weight:bold">
                      Restablecer contraseña
                    </a>
                  </div>
                  <p>Este enlace expira en <strong>30 minutos</strong>. Si no solicitaste esto, ignora este correo.</p>
                  <br>
                  <p style="color:#888;font-size:12px">Este es un mensaje automático, no respondas a este correo.</p>
                </body></html>
                """.formatted(nombre, link);
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
