package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;
import java.io.File;
import java.util.ArrayList;

public interface Saver {

    void saveExamPaper(ExamPaper examPaper);

    void delete();

    ArrayList list();
}