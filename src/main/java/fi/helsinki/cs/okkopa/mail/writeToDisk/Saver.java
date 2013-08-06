package fi.helsinki.cs.okkopa.mail.writeToDisk;

import fi.helsinki.cs.okkopa.exampaper.ExamPaper;

public interface Saver {

    void saveExamPaper(ExamPaper examPaper);

    void delete();

    void list();
}
