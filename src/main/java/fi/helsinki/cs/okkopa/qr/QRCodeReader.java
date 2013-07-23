package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.util.List;

public class QRCodeReader {

    public void readQRCodes(List<ExamPaper> papers) throws NotFoundException {
        LuminanceSource source = new BufferedImageLuminanceSource((BufferedImage) images.get(i));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap);
    }
}