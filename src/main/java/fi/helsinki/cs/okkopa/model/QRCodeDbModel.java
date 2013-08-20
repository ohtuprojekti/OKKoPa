package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "qrCodes")
public class QRCodeDbModel {

    @DatabaseField(id = true, columnName = "qrCode")
    private String qrCodeString;
    @DatabaseField(columnName = "userId")
    private String userId;

    public QRCodeDbModel() {
    }

    public QRCodeDbModel(String qrCodeString, String userId) {
        this.qrCodeString = qrCodeString;
        this.userId = userId;
    }

    public String getQRCodeString() {
        return qrCodeString;
    }

    public void setQRCodeString(String qrCodeString) {
        this.qrCodeString = qrCodeString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
