package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class Main {

    private static IMAPserver server;
    private static InputStream attachment;
    private static String name;

    public static void main(String[] args) throws NoSuchProviderException, MessagingException, IOException {
        server = new IMAPserver();
        server.login();

        testFolder("inbox");

        System.out.println("\n-- väli --\n");

        testFolder("processed");

        server.close();
    }

    private static void testFolder(String emailBox) throws MessagingException, IOException {
        IMAPfolder folder = new IMAPfolder(server, emailBox);
        IMAPmessage message;

        for (int i = 0; i < 40; i++) {
            Message msg;
            msg = folder.getNextmessage();

            if (msg == null) {
                System.out.println("-");
            } else {
                message = new IMAPmessage(msg);
                System.out.print(message.getSubject() + " + ");
                HashMap<String, InputStream> attachments = message.getAttachments();
                if (attachments != null) {
                    for (Map.Entry<String, InputStream> attachmentAndName : attachments.entrySet()) {
                        attachment = attachmentAndName.getValue();
                        name = attachmentAndName.getKey();
                        
                        Tallennus tallennus = new Tallennus();
                        
                        tallennus.tiedostonTallennus(attachment, name);
                    }
                } else {
                    System.out.println("ei liitettä");
                }
            }
        }
    }
}

class Tallennus {
    
    private InputStream inputStream;
    private FileOutputStream outputStream;
    
    public void tiedostonTallennus(InputStream input, String fileName) {
        try {
            inputStream = input;

            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File("/cs/fs/home/tatutall/NetBeansProjects/OKKoPa/" + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
