/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "batchInfo")
public class BatchDbModel {

    @DatabaseField(id = true, columnName = "id")
    private String id;
    
    @DatabaseField(columnName = "emailContent")
    private String emailContent;
    
    @DatabaseField(columnName = "reportEmailAddress")
    private String reportEmailAddress;
    
    public BatchDbModel() {
        
    }
    
    public BatchDbModel(String id, String emailContent, String reportEmailAddress) {
        this.id = id;
        this.emailContent = emailContent;
        this.reportEmailAddress = reportEmailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getReportEmailAddress() {
        return reportEmailAddress;
    }

    public void setReportEmailAddress(String reportEmailAddress) {
        this.reportEmailAddress = reportEmailAddress;
    }
    
    
}
