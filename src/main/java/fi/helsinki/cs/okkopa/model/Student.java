package fi.helsinki.cs.okkopa.model;

public class Student {

    private String email;
    private String studentNumber;
    private String username;
    
    /**
     *Initializes and hold student information.
     */
    public Student() {
    }
    
    /**
     *Intializes student information like: username, E-mail, student number.
     * @param username
     * @param email
     * @param studentNumber
     */
    public Student(String username, String email, String studentNumber) {
        this.username = username;
        this.email = email;
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
