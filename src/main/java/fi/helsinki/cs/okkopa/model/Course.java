/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "")
public class Course {
    
    @DatabaseField(columnName = "KURSSIKOODI")
    private String courseCode;
    @DatabaseField(columnName = "LUKUKAUSI")
    private String period;
    @DatabaseField(columnName = "LUKUVUOSI")
    private String year;
    @DatabaseField(columnName = "TYYPPI")
    private String type;
    @DatabaseField(columnName = "KURSSI_NRO")
    private String courseNumber;

    
    
    public Course() {
    }

}
