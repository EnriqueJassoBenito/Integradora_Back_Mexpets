package mx.edu.utez.mexprotec.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    private final JavaMailSender javaMailSender;
    private static final String HTML_DOCTYPE = "<!DOCTYPE html>" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "  </head>\n";

    private static final String BODY_STYLE = "<body style=\"font-family: 'Segoe UI', sans-serif; text-align: center\">\n";

    private static final String DIV_STYLE = "<div style=\"%s\">\n";

    private static final String IMG_TEMPLATE = "<img src=\"%s\" style=\"width: 100%%\" alt=\"%s\" />\n";

    private static final String CLOSING_TAGS = "</div>\n</body>\n</html>";

    @Autowired
    private Mailer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    public String mailFrom;

    private String buildWelcomeMessage(String name) {
        return String.format(
                BODY_STYLE +
                        DIV_STYLE +
                        IMG_TEMPLATE +
                        "<div style=\"background-color: #b53439; height: 35px\"></div>\n" +
                        "<div style=\"padding: 15px\">\n" +
                        "<p style=\"font-weight: bold; font-size: 1.5rem; margin-top: -8px\">\n" +
                        "¡Bienvenido %s!\n" +
                        "</p>\n" +
                        "<p>\n" +
                        "Estamos emocionados de tenerte con nosotros...\n" +
                        "</p>\n" +
                        "<p>\n" +
                        "<small style=\"font-style: italic; color: #6c757d\">\n" +
                        "Este mensaje es automático. No es necesario responder.\n" +
                        "</small>\n" +
                        "</p>\n" +
                        CLOSING_TAGS,
                "width: 100%",
                "https://assets-global.website-files.com/63634f4a7b868a399577cf37/63ceba1ae7b26aa4ad28478f_adopcion%20de%20razas%20de%20perros%20pequen%CC%83as.jpg",
                "Libro",
                name
        );
    }

    private String buildRequestMessage(String name, String content) {
        return String.format(
                BODY_STYLE +
                        DIV_STYLE +
                        content +
                        CLOSING_TAGS
        );
    }

    public boolean sendEmailWelcome(String email, String name, String affair) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setFrom(mailFrom);
            helper.setSubject(affair);

            String mensaje = HTML_DOCTYPE + buildWelcomeMessage(name);

            helper.setText(mensaje, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendAcceptedRequest(String email, String name) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setFrom(mailFrom);
            helper.setSubject("Solicitud procesada");

            String content = String.format(
                    IMG_TEMPLATE +
                            "<p>Hola %s, tu solicitud ha sido procesada.</p>\n" +
                            "<p>Gracias por utilizar nuestra aplicación.</p>\n",
                    "width: 100%",
                    "https://demo.stripocdn.email/content/guids/d0dee27c-b951-4be2-9e65-fe5d431243a4/images/booksg5a3638f0519201633971795.jpg",
                    "mexpet",
                    name
            );

            String mensaje = HTML_DOCTYPE + buildRequestMessage(name, content);

            helper.setText(mensaje, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendDismissedRequest(String email, String name) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setFrom(mailFrom);
            helper.setSubject("Solicitud procesada");

            String content = String.format(
                    IMG_TEMPLATE +
                            "<p>Hola %s, tu solicitud no ha sido procesada. Vuelve a intentar</p>\n" +
                            "<p>Gracias por utilizar nuestra aplicación.</p>\n",
                    "width: 100%",
                    "https://demo.stripocdn.email/content/guids/d0dee27c-b951-4be2-9e65-fe5d431243a4/images/booksg5a3638f0519201633971795.jpg",
                    name
            );

            String mensaje = HTML_DOCTYPE + buildRequestMessage(name, content);

            helper.setText(mensaje, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
