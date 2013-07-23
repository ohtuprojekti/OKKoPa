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
import java.util.logging.Level;
import java.util.logging.Logger;

public class QRCodeReader {

    public void readQRCodes(ExamPaper paper) throws NotFoundException {
        Result result = null;
        NotFoundException e = null;
       
            for (int j = 0; j < paper.getPages().size(); j++) {
                LuminanceSource source = new BufferedImageLuminanceSource((BufferedImage) paper.getPages().get(j));
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                try {
                    result = reader.decode(bitmap);
                } catch (NotFoundException ex) {
                    e = ex;
                }                
            }
            
            if (result != null) {
                paper.setResult(result);
            } else {
                throw e;
            }
    }
}