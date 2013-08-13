package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "failedEmail")
public class FailedEmail {

    @DatabaseField(id = true, columnName = "filename")
    private String filename;
    @DatabaseField(columnName = "receiverEmail")
    private String receiverEmail;

    public FailedEmail() {
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
}
