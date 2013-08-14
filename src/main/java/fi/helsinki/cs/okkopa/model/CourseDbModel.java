/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public CourseDbModel() {
    }

    public CourseDbModel(String courseCode, String period, int year, String type, int courseNumber) {
        this.courseCode = courseCode;
        this.period = period;
        this.year = year;
        this.type = type;
        this.courseNumber = courseNumber;
    }

    public CourseDbModel(int year) {
        this.year = year;   
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

    public String getType() {
        return type;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
