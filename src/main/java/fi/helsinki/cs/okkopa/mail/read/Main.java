package fi.helsinki.cs.okkopa.mail.read;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class Main {

    public static void main(String[] args) throws NoSuchProviderException, MessagingException, IOException {

        System.out.println("\nalku\n");

        MailRead server = new MailRead();
        server.connect();

        for (int i = 0; i < 10; i++) {
            server.getNextAttachment();
        }

        server.close();
    }

//    class Tallennus {
//
//        private InputStream inputStream;
//        private FileOutputStream outputStream;
//
//        public void tiedostonTallennus(InputStream input, String fileName) {
//            try {
//                inputStream = input;
//
//                // write the inputStream to a FileOutputStream
//                outputStream = new FileOutputStream(new File("/cs/fs/home/tatutall/NetBeansProjects/OKKoPa/" + fileName));
//
//                int read = 0;
//                byte[] bytes = new byte[1024];
//
//                while ((read = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, read);
//                }
//
//                System.out.println("Done!");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (outputStream != null) {
//                    try {
//                        // outputStream.flush();
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }
//    }
}