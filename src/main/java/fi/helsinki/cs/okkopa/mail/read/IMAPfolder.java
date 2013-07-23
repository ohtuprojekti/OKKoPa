package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

/**
 * Opens folder and gets its messages one by one.
 */
public class IMAPfolder {

    private IMAPserver server;
    private IMAPFolder folder;
    private Message[] messages;
    private boolean messagesGot = false;
    private Message msg;
    private int index = 0;

    /**
     * Opens and formats folder to use.
     * 
     * @param server used IMAPserver node.
     * @param folder what folder we want to use.
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public IMAPfolder(IMAPserver server, String folder) throws NoSuchProviderException, MessagingException {
        this.server = server;
        this.folder = this.server.selectAndGetFolder(folder);
    }
    
    /**
     * Return oldest not already returned message, if no new messages, then return null.
     * 
     * @return next message.
     * @throws MessagingException
     */
    public Message getNextmessage() throws MessagingException {
        getMessagesIfNotGot();

        if (folder.getMessageCount() > index) {
            msg = messages[index];
            index++;
            return msg;
        } else {
            return null;
        }
    }

    private Message[] getMessages() throws MessagingException {
        messages = folder.getMessages();
        messagesGot = true;
        return messages;
    }

    private void getMessagesIfNotGot() throws MessagingException {
        if (messagesGot == false) {
            this.getMessages();
        }
    }
}
