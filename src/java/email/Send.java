/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author sosyn
 */
/**
 *
 * Send.java from .. Demonstrates using the JavaMail API to send an email
 * message.
 *
 * Usage:
 *
 * Send send= new Send(to, cc, bcc, from, subject, body, smtpHost);
 * send.sendMessage();
 *
 * The fields "to", "cc", and "bcc" can be comma-separated sequences of
 * addresses.
 *
 * @see mail.internet.InternetAddress.parse() for details.
 *
 */
public class Send {

    private String to;
    private String cc;
    private String bcc;
    private String from;
    private String subject;
    private String content;
    private String smtpHost;
    private Message message;

    public Send(String to,
            String cc,
            String bcc,
            String from,
            String subject,
            String content,
            String smtpHost)
            throws AddressException, MessagingException {

        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.smtpHost = smtpHost;

        message = createMessage();
        message.setFrom(new InternetAddress(from));
        setToCcBccRecipients();

        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setText(content);
    }

    public void sendMessage()
            throws MessagingException {
        try {
            Transport.send(message);
        } catch (MessagingException me) {
            // do logging here
            throw me;
        }
    }

    private Message createMessage() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "192.168.3.46");
        Session session = Session.getDefaultInstance(properties, null);
        return new MimeMessage(session);
    }

    private void setToCcBccRecipients()
            throws AddressException, MessagingException {
        setMessageRecipients(to, Message.RecipientType.TO);
        setMessageRecipients(cc, Message.RecipientType.CC);
        setMessageRecipients(bcc, Message.RecipientType.BCC);
    }

    private void setMessageRecipients(String recipient, Message.RecipientType recipientType)
            throws AddressException, MessagingException {
        InternetAddress[] addressArray = buildInternetAddressArray(recipient);

        if ((addressArray != null) && (addressArray.length > 0)) {
            message.setRecipients(recipientType, addressArray);
        }
    }

    /**
     * The address can be a comma-separated sequence of email addresses.
     *
     * @see mail.internet.InternetAddress.parse() for details.
     *
     */
    private InternetAddress[] buildInternetAddressArray(String address)
            throws AddressException {
        // could test for a null or blank String but I'm letting parse just throw an exception
        InternetAddress[] internetAddressArray = null;
        try {
            internetAddressArray = InternetAddress.parse(address);
        } catch (AddressException ae) {
            // do logging here
            throw ae;
        }
        return internetAddressArray;
    }

    public static void main(String args[]) throws Exception {
        Send send;
        send = new Send("sosyn@mukrnov.cz", "", "", "system@mukrnov.cz", "Test systémového rezervačního e-mailu", "Dobrý den, toto je pouze test", "");
        send.sendMessage();
    }
}
