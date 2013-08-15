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
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OPISKELIJA")
public class StudentDbModel {

    @DatabaseField(columnName = "OPNRO")
    private String studentNumber;

    public StudentDbModel() {
    }

    public StudentDbModel(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
