package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class Main {

    public static void main(String[] args) throws NoSuchProviderException, MessagingException, IOException {

        System.out.println("\nalku\n");

        MailRead server = new MailRead();
        server.connect();

        server.deleteOldMessages();

        server.close();
    }
}