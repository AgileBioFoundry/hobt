package org.abf.hobt.notification;

import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.common.util.StringUtils;
import org.abf.hobt.config.ConfigurationValue;
import org.abf.hobt.config.Settings;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

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
            Logger.error("SMTP host is not configured. Aborting email send.");
            return;
        }

        String senderEmail = new Settings().getValue(ConfigurationValue.FROM_EMAIL);

        // Configure the mailer
        Mailer mailer = MailerBuilder
            .withSMTPServer(smtpHost, 25) // Change the port if required
            // Uncomment and set if authentication is required:
            // .withSMTPServerUsername("username")
            // .withSMTPServerPassword("password")
            .buildMailer();

        try {
            // Build the email
            EmailPopulatingBuilder builder = EmailBuilder.startingBlank()
                .from(projectName, senderEmail)
                .to(projectName, email)
                .withSubject(subject)
                .withPlainText(body);

            // Add CC if provided
            if (!StringUtils.isBlank(ccEmail)) {
                builder.cc("", ccEmail);
            }

            // Build the final email
            org.simplejavamail.api.email.Email emailMessage = builder.buildEmail();

            // Send the email
            mailer.sendMail(emailMessage);
            Logger.info("Email sent successfully.");
        } catch (Exception e) {
            Logger.error("Error sending email", e);
        }
    }
}
