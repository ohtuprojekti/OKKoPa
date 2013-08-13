package fi.helsinki.cs.okkopa.main;

import fi.helsinki.cs.okkopa.main.stage.GetEmailStage;
import fi.helsinki.cs.okkopa.main.stage.ReadCourseInfoStage;
import fi.helsinki.cs.okkopa.main.stage.ReadQRCodeStage;
import fi.helsinki.cs.okkopa.main.stage.SaveToTikliStage;
import fi.helsinki.cs.okkopa.main.stage.SendEmailStage;
import fi.helsinki.cs.okkopa.main.stage.SetStudentInfoStage;
import fi.helsinki.cs.okkopa.main.stage.SplitPDFStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private GetEmailStage getEmailStage;

    @Autowired
    public OkkopaRunner(GetEmailStage getEmailStage, SplitPDFStage splitPDFStage,
            ReadCourseInfoStage readCourseInfoStage, ReadQRCodeStage readQRCodeStage,
            SetStudentInfoStage setStudentInfoStage, SendEmailStage sendEmailStage,
            SaveToTikliStage saveToTikliStage) {
        this.getEmailStage = getEmailStage;
        getEmailStage.setNext(splitPDFStage);
        splitPDFStage.setNext(readCourseInfoStage);
        readCourseInfoStage.setNext(readQRCodeStage);
        readQRCodeStage.setNext(setStudentInfoStage);
        setStudentInfoStage.setNext(sendEmailStage);
        sendEmailStage.setNext(saveToTikliStage);
//        this.ldapConnector = ldapConnector;
//        this.saver = saver;
//        saveRetryFolder = settings.getSettings().getProperty("mail.send.retrysavefolder");
//        saveToTikli = Boolean.parseBoolean(settings.getSettings().getProperty("tikli.enable"));

//        retryExpirationMinutes = Integer.parseInt(settings.getSettings().getProperty("mail.send.retryexpirationminutes"));
//        this.errorPDFRemover = new ErrorPDFRemover(settings);
//        this.failedEmailDatabase = failedEmailDatabase;
    }

    @Override
    public void run() {
        getEmailStage.process(null);
//        LOGGER.debug("Poistetaan vanhoja epäonnistuneita viestejä");
//        errorPDFRemover.deleteOldMessages();
    }

}