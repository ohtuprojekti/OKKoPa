package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Moves messages from given folder to another.
 */
public class IMAPcopy {

    private IMAPserver server;

    /**
     * Formats to use, gets server-node.
     *
     * @param server Where folders and messages are.
     */
    public IMAPcopy(IMAPserver server) {
        this.server = server;
    }

    /**
     * Copies one message from it's own folder to another. In Gmail it must also
     * remove it from the original folder.
     *
     * @param message what message we want to move.
     * @param fromBox It's folder where it is now.
     * @param toBox Where we want the message to be moved.
     * @throws MessagingException
     */
    public void copyMessage(Message message, String fromBox, String toBox) throws MessagingException {
        IMAPFolder folderFrom = this.server.selectAndGetFolder(fromBox);
        IMAPFolder folderTo = this.server.selectAndGetFolder(toBox);

        Message deleteMessage = message;
        Message[] deleteMessageArray = new Message[1];
        deleteMessageArray[0] = deleteMessage;

        folderFrom.copyMessages(deleteMessageArray, folderTo);

        IMAPdelete.deleteMessage(deleteMessage);
    }
}
