/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.main;

import org.springframework.stereotype.Component;

@Component
public class BatchDetails {
    
    private int totalPages;
    private int failedScans; 
    private String reportEmailAddress;
    private String defaultEmailContent;
    private Settings settings;
    
    
    //Courseinfo:
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }
    private String courseCode;
    private String period;
    private int year;
    private String type;
    private int courseNumber;
    
    public BatchDetails(Settings settings) {
        this.settings = settings;
        reset();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getFailedScans() {
        return failedScans;
    }

    public void setFailedScans(int failedScans) {
        this.failedScans = failedScans;
    }

    public String getReportEmailAddress() {
        return reportEmailAddress;
    }

    public void setReportEmailAddress(String reportEmailAddress) {
        this.reportEmailAddress = reportEmailAddress;
    }

    public String getDefaultEmailContent() {
        return defaultEmailContent;
    }

    public void setDefaultEmailContent(String emailContent) {
        this.defaultEmailContent = emailContent;
    }

    public void reset() {
        this.defaultEmailContent = settings.getProperty("mail.defaultmessage.body");
        this.reportEmailAddress = null;
        this.totalPages = 0;
        this.failedScans = 0;
        this.courseCode = null;
        this.courseNumber = 0;
        this.period = null;
        this.type = null;
        this.year = 0;
        
    }
    
    
    
}
