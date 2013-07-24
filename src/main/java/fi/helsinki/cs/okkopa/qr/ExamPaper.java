package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.Result;
import java.io.InputStream;

public class ExamPaper {

    private InputStream pdfStream;
    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public InputStream getPdfStream() {
        return pdfStream;
    }

    public void setPdfStream(InputStream pdfStream) {
        this.pdfStream = pdfStream;
    }
}
