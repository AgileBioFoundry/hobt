package org.abf.hobt.notification;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author Hector Plahar
 */
public class Email {

    private final String projectName;

    public Email() {
        projectName = "Host Onboarding Tool";
    }

    public void send(String email, String ccEmail, String subject, String body) {
        Logger.info("Sending email to " + email + ": cced " + ccEmail + " with subject " + subject);
        String smtpHost = new Settings().getValue(ConfigurationValue.SMTP_HOST);
        if (StringUtils.isBlank(smtpHost)) {
            Logger.error("Smtp host has not been set. Aborting email send.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        Session session = Session.getDefaultInstance(props, null);

        Message msg = new MimeMessage(session);

        String senderEmail = new Settings().getValue(ConfigurationValue.FROM_EMAIL);

        try {
            msg.setFrom(new InternetAddress(senderEmail, projectName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, projectName));
            if (!StringUtils.isBlank(ccEmail))
                msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
//            if (!StringUtil.isEmpty(adminEmail))
//                msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(adminEmail));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            Logger.error(e);
        }
    }
}
