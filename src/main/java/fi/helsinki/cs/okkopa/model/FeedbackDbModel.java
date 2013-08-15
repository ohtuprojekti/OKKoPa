package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Calendar;
import java.util.Date;

@DatabaseTable(tableName = "SUORITUSPALAUTE")
public class FeedbackDbModel {

    @DatabaseField(columnName = "SUORITUSPALAUTE_ID", generatedIdSequence = "SUORITUSPALAUTE_SEQUENCE")
    private int id;
    @DatabaseField(columnName = "OPISKELIJANUMERO")
    private String studentNumber;
    @DatabaseField(columnName = "KURSSIKOODI")
    private String courseCode;
    @DatabaseField(columnName = "LUKUKAUSI")
    private String period;
    @DatabaseField(columnName = "LUKUVUOSI")
    private int year;
    @DatabaseField(columnName = "TYYPPI")
    private String courseType;
    @DatabaseField(columnName = "KURSSI_NRO")
    private int courseNumber;
    @DatabaseField(columnName = "SUORITUSPALAUTE_KOHDE")
    private String feedbackType;
    @DatabaseField(columnName = "SUORITUSPALAUTE_PVM")
    private Date date;
    @DatabaseField(columnName = "SUORITUSPALAUTE_ANTAJA")
    private String feedbackAuthor;
    @DatabaseField(columnName = "SUORITUSPALAUTE_TEKSTI")
    private String feedbackText;
    @DatabaseField(columnName = "SUORITUSPALAUTE_URL")
    private String feedbackUrl;
    @DatabaseField(columnName = "SUORITUSPALAUTE_DATA", dataType = DataType.BYTE_ARRAY)
    private byte[] data;

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getPeriod() {
        return period;
    }

    public int getYear() {
        return year;
    }

    public String getCourseType() {
        return courseType;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public Date getDate() {
        return date;
    }

    public String getFeedbackAuthor() {
        return feedbackAuthor;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public byte[] getData() {
        return data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }
    @DatabaseField(columnName = "SUORITUSPALAUTE_DATA_MIME")
    private String mimeType;
    @DatabaseField(columnName = "SUORITUSPALAUTE_DATA_NIMI")
    private String fileName;

    public FeedbackDbModel() {
    }

    public FeedbackDbModel(CourseDbModel course, byte[] blob, String studentNumber) {
        this.studentNumber = studentNumber;
        this.date = Calendar.getInstance().getTime();

        //Course data:
        this.courseCode = course.getCourseCode();
        this.courseNumber = course.getCourseNumber();
        this.courseType = course.getType();
        this.period = course.getPeriod();
        this.year = course.getYear();

        //File data:
        this.data = blob;
        this.mimeType = "application/pdf";
        this.fileName = "Exampaper.pdf";

        //Check these before release:
        this.feedbackAuthor = "WIKLA_A";
        this.feedbackText = "Feedback text / content";
        this.feedbackType = "Skannattu koepaperi";
        this.feedbackUrl = null;

    }
}
