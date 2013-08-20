/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;


@DatabaseTable(tableName = "MissedExam")
public class MissedExamDbModel {
    @DatabaseField(columnName = "qrCode", foreign = true)
    private QRCodeDbModel qrCode;
    @DatabaseField(columnName = "date")
    private Date date;

    public MissedExamDbModel() {
    }
    
    public MissedExamDbModel(QRCodeDbModel qRCode) {
        this.qrCode = qRCode;
        this.date = new Date();
    }

    public QRCodeDbModel getQrCode() {
        return qrCode;
    }

    public void setQrCode(QRCodeDbModel qrCode) {
        this.qrCode = qrCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
