package fr.sweetiez.api.infrastructure.notification.email;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import fr.sweetiez.api.infrastructure.notification.email.dtos.ConfirmPasswordChangeDto;
import fr.sweetiez.api.infrastructure.notification.email.dtos.OrderEmailDto;
import fr.sweetiez.api.infrastructure.notification.email.dtos.ResetPasswordEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.io.StringWriter;

public class GmailSender implements EmailNotifier {

    @Value("${front.base-url}")
    private String frontUrl;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(OrderEmailDto emailDto) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("mustache/order_client.mustache");

        StringWriter writer = new StringWriter();
        String messageText = "";
        try {
            m.execute(writer, emailDto).flush();
            messageText = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.to());
        message.setSubject(emailDto.subject());

        message.setText(messageText);

        mailSender.send(message);
    }

    @Override
    public void send(ResetPasswordEmailDto emailDto) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("mustache/reset_password.mustache");

        var dto = new ResetPasswordEmailDto(emailDto, String.format("%s/reset-password/%s", frontUrl, emailDto.resetPasswordToken()));

        StringWriter writer = new StringWriter();
        String messageText = "";
        try {
            m.execute(writer, dto).flush();
            messageText = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.email());
        message.setSubject("Fi-Sweets - Réinitialisation du mot de passe");

        message.setText(messageText);

        mailSender.send(message);
    }

    @Override
    public void send(ConfirmPasswordChangeDto confirmPasswordChangeDto) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("mustache/change_password.mustache");

        StringWriter writer = new StringWriter();
        String messageText = "";
        try {
            m.execute(writer, confirmPasswordChangeDto).flush();
            messageText = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(confirmPasswordChangeDto.email());
        message.setSubject("Fi-Sweets - Changement de mot de passe confirmé");

        message.setText(messageText);

        mailSender.send(message);
    }

}
