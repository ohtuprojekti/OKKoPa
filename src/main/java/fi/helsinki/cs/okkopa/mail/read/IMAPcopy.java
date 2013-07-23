package fi.helsinki.cs.okkopa.mail.read;

import com.sun.mail.imap.IMAPFolder;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class IMAPcopy {

    private IMAPFolder folderFrom;
    private IMAPFolder folderTo;
    private IMAPserver server;
    private Message message;
    private Message[] messages;

    public IMAPcopy(IMAPserver server) {
        this.server = server;
    }

    public void copyMessage(Message message, String fromBox, String toBox) throws MessagingException {
        this.folderFrom = this.server.selectAndGetFolder(fromBox);
        this.folderTo = this.server.selectAndGetFolder(toBox);
        
        this.message = message;
        this.messages = new Message[1];
        this.messages[0] = this.message;

        folderFrom.copyMessages(this.messages, folderTo);

        message.setFlag(Flags.Flag.DELETED, true);
    }
}
