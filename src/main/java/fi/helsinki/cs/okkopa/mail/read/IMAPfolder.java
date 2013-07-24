package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private IMAPcopy copy;
    private final String folderName;
    private Message old_msg = null;

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
        this.folderName = folder;
        this.folder = this.server.selectAndGetFolder(folder);
        this.copy = new IMAPcopy(this.server);
    }

    /**
     * Return oldest not already returned message, if no new messages, then
     * return null. Move last before returned message to processed folder.
     *
     * @return next message.
     * @throws MessagingException
     */
    public Message getNextmessage() throws MessagingException {
        return this.getNextmessage("processed");
    }

    /**
     * Return oldest not already returned message, if no new messages, then
     * return null.
     *
     * @param whereToMoveAfterProcessed Where we want to move the last given
     * message. If null not moved.
     * @return next message.
     * @throws MessagingException
     */
    public Message getNextmessage(String whereToMoveAfterProcessed) throws MessagingException {
        getMessagesIfNotGot();

        if (old_msg != null) {
            this.copy.copyMessage(old_msg, this.folderName, whereToMoveAfterProcessed);
        }

        if (folder.getMessageCount() > index) { 
            msg = messages[index];
            old_msg = msg;
            
            index++;
            return msg;
        } else {
            old_msg = null;
            return null;
        }
    }

    private Message[] getMessages() throws MessagingException {
        messages = folder.getMessages();
        this.sortMessagesByDate(messages);
        messagesGot = true;

        return messages;

    }

    private void sortMessagesByDate(Message[] messages) {
        Arrays.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                try {
                    return o2.getReceivedDate().compareTo(o1.getReceivedDate());
                } catch (MessagingException ex) {
                    Logger.getLogger(IMAPfolder.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        });
    }

    private boolean getMessagesIfNotGot() throws MessagingException {
        if (messagesGot == false) {
            this.getMessages();
            return false;
        }
        return true;
    }

    /**
     * Closes this folder.
     * 
     * @throws MessagingException
     */
    public void close() throws MessagingException {
        this.server.closeFolder(this.folder);
    }
}
