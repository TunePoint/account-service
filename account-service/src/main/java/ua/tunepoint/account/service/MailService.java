package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.service.client.MailClient;
import ua.tunepoint.mail.model.request.SendMailTemplateRequest;
import ua.tunepoint.web.exception.ServerException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailClient mailClient;

    public void sendEmail(String recipient, String subject, String template, Map<String, String> params) {
        try {
            var request = constructRequest(
                    recipient, subject, template, params
            );
            mailClient.sendMailTemplate(request);
        } catch (Exception ex) {
            throw new ServerException(ex.getMessage());
        }
    }

    private SendMailTemplateRequest constructRequest(String recipient, String subject, String template, Map<String, String> params) {
        var request = new SendMailTemplateRequest();
        request.setRecipientEmail(recipient);
        request.setSubject(subject);
        request.setTemplate(template);
        request.setParams(params);
        return request;
    }
}
