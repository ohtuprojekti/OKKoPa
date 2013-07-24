package fi.helsinki.cs.okkopa.qr;

import java.io.InputStream;

public class ExamPaper {

    private InputStream pdfStream;
    private String QRCodeString;

    public void setQRCodeString(String QRCodeString) {
        this.QRCodeString = QRCodeString;
    }

    public String getQRCodeString() {
        return QRCodeString;
    }

    public InputStream getPdfStream() {
        return pdfStream;
    }

    public void setPdfStream(InputStream pdfStream) {
        this.pdfStream = pdfStream;
    }
}
