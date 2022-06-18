package fr.sweetiez.api.infrastructure.notification.email;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.io.StringWriter;

public class GmailSender implements EmailNotifier {

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

}
