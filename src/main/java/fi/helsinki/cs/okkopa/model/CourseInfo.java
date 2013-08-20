package fi.helsinki.cs.okkopa.model;

/**
 *
 * Sets and hold course information. 
 */
public class CourseInfo {

    private String courseCode;
    private String period;
    private int year;
    private String type;
    private int courseNumber;

    
    /**
     * Initializes course code , period, year, type and course number.
     * 
     * @param courseCode
     * @param period
     * @param year
     * @param type
     * @param courseNumber
     */
    public CourseInfo(String courseCode, String period, int year, String type, int courseNumber) {
        this.courseCode = courseCode;
        this.period = period;
        this.year = year;
        this.type = type;
        this.courseNumber = courseNumber;
    }
    
    /**
     *Gets course code which initialized in constructor.
     * @return course code.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     *Sets course code
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     *Gets course number.
     * @return course number
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    /**
     *Sets course number.
     * @param courseNumber
     */
    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     *Gets period of year.
     * @return
     */
    public String getPeriod() {
        return period;
    }

    /**
     *Sets period.
     * @param period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     *Gets year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     *Sets year.
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *Get type of course
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *Sets type of course.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    } 
}
