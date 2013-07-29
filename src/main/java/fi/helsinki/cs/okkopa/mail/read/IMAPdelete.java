package fi.helsinki.cs.okkopa.mail.read;

import java.util.Date;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class IMAPdelete {

    private IMAPserver server;
    private IMAPfolder folder;
    private IMAPmessage message;
    private int i;
    private Date timeNow;
    private long timeDifferenceInMilliSeconds;
    private long differenceInDays;

    public IMAPdelete(IMAPserver server) {
        this.server = server;
    }

    public void deleteOldMessages(int howManyDaysOldAreToBeDeleted) throws MessagingException {
        System.out.println("avataan kansio");
        
        this.folder = new IMAPfolder(server, "processed");

        System.out.println("kansio avattu");
        for (message = this.folder.getNextmessage(null); howManyDaysOldAreToBeDeleted <= this.HowOld(message); i++) {
            System.out.println("viesti otettu");
            IMAPdelete.deleteMessage(message.getIMAPMessage());
            System.out.println("viesti poistettu");
            message = this.folder.getNextmessage(null);
        }       
    }

    private int HowOld(IMAPmessage message) throws MessagingException {
        System.out.println("päivämääriä vertaillaan");
        timeNow = new Date();
        
        timeDifferenceInMilliSeconds = timeNow.getTime() - message.getTime().getTime();
        
        differenceInDays = timeDifferenceInMilliSeconds / 1000 / 60 / 60 / 24;
        
        System.out.println("ja laskut on suoritettu");
        
        System.out.println(differenceInDays);
        
        return (int) differenceInDays;
    }

    public static void deleteMessage(Message message) throws MessagingException {
        System.out.println("poistetaan");
        message.setFlag(Flags.Flag.DELETED, true);
        System.out.println("poistettu");
    }
}
