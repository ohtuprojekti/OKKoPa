package fi.helsinki.cs.okkopa.mail.read;

import java.util.Date;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Tests how old messages are kept in processed folder and deletes any older messages.
 */
public class IMAPdelete {

    private IMAPserver server;
    private IMAPfolder folder;
    private IMAPmessage message;
    private int i;
    private Date timeNow;
    private long timeDifferenceInMilliSeconds;
    private long differenceInDays;

    /**
     * Formats object to use.
     * @param server so it can no where to work.
     */
    public IMAPdelete(IMAPserver server) {
        this.server = server;
    }

    /**
     * Deletes all messages that are older than given parameter.
     * 
     * @param howManyDaysOldAreToBeDeleted any older messages are deleted.
     * @throws MessagingException
     */
    public void deleteOldMessages(int howManyDaysOldAreToBeDeleted) throws MessagingException {
        
        this.folder = new IMAPfolder(server, "processed");

        for (message = this.folder.getNextmessage(null); howManyDaysOldAreToBeDeleted <= this.HowOld(message); i++) {
            IMAPdelete.deleteMessage(message.getIMAPMessage());
            message = this.folder.getNextmessage(null);
        }       
    }

    private int HowOld(IMAPmessage message) throws MessagingException {
        timeNow = new Date();
        
        timeDifferenceInMilliSeconds = timeNow.getTime() - message.getTime().getTime();
        
        differenceInDays = timeDifferenceInMilliSeconds / 1000 / 60 / 60 / 24;
        
        return (int) differenceInDays;
    }

    /**
     * Delete given message from IMAPserver.
     * @param message
     * @throws MessagingException
     */
    public static void deleteMessage(Message message) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
    }
}
