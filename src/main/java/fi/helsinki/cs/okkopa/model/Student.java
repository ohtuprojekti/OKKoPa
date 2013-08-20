package fi.helsinki.cs.okkopa.model;

/**
 * A Container class for student information.
 * Contains e-mail address, student number and username.
 *
 */
public class Student {

    private String email;
    private String studentNumber;
    private String username;
    
    /**
     *Initializes and hold student information.
     */
    public Student() {
    }

    public Student(String username, String email, String studentNumber) {
        this.username = username;
        this.email = email;
        this.studentNumber = studentNumber;
    }

    /**
     * Returns e-mail address.  
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets e-mail address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns student number.
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * Sets student number.
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Returns username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
