/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.search.*;

/**
 *
 * @author sosyn
 */
public class Receive {

    Properties properties = System.getProperties();
    Session session = null;
    Store store = null;
    Folder inbox = null;
    Flags seen = null;
    FlagTerm unseenFlagTerm = null;

    Message[] messages = null;

    // mail server connection parameters
    String host = "192.168.3.46";
    String user = "system@mukrnov.cz";
    String password = "Krnov123*";

    public Receive() {
        session = Session.getDefaultInstance(properties);
        seen = new Flags(Flags.Flag.SEEN);
        unseenFlagTerm = new FlagTerm(seen, false);
    }

    void receiveEmail() throws NoSuchProviderException, MessagingException {
        // connect to my pop3 inbox
        store = session.getStore("pop3");
        store.connect(host, user, password);
        inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        // get the list of inbox messages
        // search for all "unseen" messages
        messages = inbox.search(unseenFlagTerm);

        if (messages.length == 0) {
            System.out.println("No messages found.");
        }

        for (int i = 0; i < messages.length; i++) {
            // stop after listing ten messages
            if (i > 10) {
                System.exit(0);
                inbox.close(true);
                store.close();
            }

            System.out.println("Message " + (i + 1));
            System.out.println("From : " + messages[i].getFrom()[0]);
            System.out.println("Subject : " + messages[i].getSubject());
            System.out.println("Sent Date : " + messages[i].getSentDate());
            System.out.println();
        }

        inbox.close(true);
        store.close();
    }

    public static void main(String args[]) throws Exception {
        Receive receive=new Receive();
        receive.receiveEmail();
    }
}
