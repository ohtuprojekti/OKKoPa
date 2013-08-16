package fi.helsinki.cs.okkopa.model;

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
    
    public StudentDbModel(Student student) {
        this(student.getStudentNumber());
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
