package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "failedEmail")
public class FailedEmailDbModel {

    @DatabaseField(id = true, columnName = "filename")
    private String filename;
    @DatabaseField(columnName = "receiverEmail")
    private String receiverEmail;
    @DatabaseField(columnName = "failTime")
    private Date failTime;

    public FailedEmailDbModel() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }
}
