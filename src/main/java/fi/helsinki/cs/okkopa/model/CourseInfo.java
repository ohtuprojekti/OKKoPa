/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.model;

/**
 *
 * @author phemmila
 *  
 */
public class CourseInfo {

    private String courseCode;
    private String period;
    private int year;
    private String type;
    private int courseNumber;

    
    public CourseInfo(String courseCode, String period, int year, String type, int courseNumber) {
        this.courseCode = courseCode;
        this.period = period;
        this.year = year;
        this.type = type;
        this.courseNumber = courseNumber;
    }
    
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } 
    
}
