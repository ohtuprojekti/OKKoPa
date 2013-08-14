package fi.helsinki.cs.okkopa.main;

import fi.helsinki.cs.okkopa.main.stage.GetEmailStage;
import fi.helsinki.cs.okkopa.main.stage.ReadCourseInfoStage;
import fi.helsinki.cs.okkopa.main.stage.ReadQRCodeStage;
import fi.helsinki.cs.okkopa.main.stage.RetryFailedEmailsStage;
import fi.helsinki.cs.okkopa.main.stage.SaveToTikliStage;
import fi.helsinki.cs.okkopa.main.stage.SendEmailStage;
import fi.helsinki.cs.okkopa.main.stage.SetStudentInfoStage;
import fi.helsinki.cs.okkopa.main.stage.SplitPDFStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaRunner implements Runnable {

    private RetryFailedEmailsStage retryFailedEmailsStage;

    @Autowired
    public OkkopaRunner(RetryFailedEmailsStage retryFailedEmailsStage,
            GetEmailStage getEmailStage, SplitPDFStage splitPDFStage,
            ReadCourseInfoStage readCourseInfoStage, ReadQRCodeStage readQRCodeStage,
            SetStudentInfoStage setStudentInfoStage, SendEmailStage sendEmailStage,
            SaveToTikliStage saveToTikliStage) {
        // cleanup and error correction stages
        this.retryFailedEmailsStage = retryFailedEmailsStage;
        retryFailedEmailsStage.setNext(getEmailStage);
        // normal process stages
        getEmailStage.setNext(splitPDFStage);
        splitPDFStage.setNext(readCourseInfoStage);
        readCourseInfoStage.setNext(readQRCodeStage);
        readQRCodeStage.setNext(setStudentInfoStage);
        setStudentInfoStage.setNext(sendEmailStage);
        sendEmailStage.setNext(saveToTikliStage);

//        this.errorPDFRemover = new ErrorPDFRemover(settings);
    }

    @Override
    public void run() {
        retryFailedEmailsStage.process(null);
//        LOGGER.debug("Poistetaan vanhoja epäonnistuneita viestejä");
//        errorPDFRemover.deleteOldMessages();
    }
}