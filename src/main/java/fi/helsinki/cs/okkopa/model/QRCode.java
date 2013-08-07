
package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "qrcodes")
public class QRCode {
    
    @DatabaseField(id = true, columnName = "qrCode")
    private String QRCodeString;
    @DatabaseField(columnName = "userId")
    private String userId;
    
    public QRCode() {
    }

    public QRCode(String QRCodeString, String userId) {
        this.QRCodeString = QRCodeString;
        this.userId = userId;
    }

    public String getQRCodeString() {
        return QRCodeString;
    }

    public void setQRCodeString(String QRCodeString) {
        this.QRCodeString = QRCodeString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
