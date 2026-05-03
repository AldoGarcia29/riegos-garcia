package com.riegosgarcia.riegos_garcia.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactoController {

    private final JavaMailSender mailSender;

    @Value("${correo.destino}")
    private String correoDestino;

    public ContactoController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostMapping("/enviar-contacto")
    public String enviarContacto(
            @RequestParam String nombre,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String mensaje
    ) {
        SimpleMailMessage email = new SimpleMailMessage();

email.setTo(correoDestino);
email.setSubject("Nueva solicitud desde la web - Riegos García");

email.setReplyTo(correo); // 🔥 CLAVE

email.setText(
    "Nuevo mensaje recibido desde la página web:\n\n" +
    "Nombre: " + nombre + "\n" +
    "Teléfono: " + telefono + "\n" +
    "Correo: " + correo + "\n\n" +
    "Mensaje:\n" + mensaje
);

mailSender.send(email);

        return "redirect:/contacto?enviado=true";
    }
}