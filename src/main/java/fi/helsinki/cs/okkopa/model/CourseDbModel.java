package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import fi.helsinki.cs.okkopa.main.BatchDetails;


@DatabaseTable(tableName = "KURSSI")
public class CourseDbModel {

    @DatabaseField(columnName = "KURSSIKOODI")
    private String courseCode;
    @DatabaseField(columnName = "LUKUKAUSI")
    private String period;
    @DatabaseField(columnName = "LUKUVUOSI")
    private int year;
    @DatabaseField(columnName = "TYYPPI")
    private String type;
    @DatabaseField(columnName = "KURSSI_NRO")
    private int courseNumber;
    @DatabaseField(columnName = "NIMI")
    private String name;

    /**
     *
     */
    public CourseDbModel() {
    }

    /**
     * Initializes course code , period, year, type and course number. 
     * @param courseCode
     * @param period
     * @param year
     * @param type
     * @param courseNumber
     */
    public CourseDbModel(String courseCode, String period, int year, String type, int courseNumber) {
        this.courseCode = courseCode;
        this.period = period;
        this.year = year;
        this.type = type;
        this.courseNumber = courseNumber;
    }
    /**
     *Initializes course information which defined in class BatchDetails
     * @param batch
     */
    public CourseDbModel(BatchDetails batch) {
        this(batch.getCourseCode(), batch.getPeriod(), batch.getYear(), batch.getType(), batch.getCourseNumber());

    }

    /**
     * Gets course code.
     * @return course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     *Gets period.
     * @return period.
     */
    public String getPeriod() {
        return period;
    }

    /**
     *
     *Gets year.
     * @param year
     */
    public int getYear() {
        return year;
    }

    /**
     *
     *Gets type of course
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *Gets course number.
     * @return course number.
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    /**
     *Gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *Sets name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
