package fi.helsinki.cs.okkopa.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.exceptions.COSVisitorException;

public class PDFProcessor {

    PDFSplitter splitter;
    QRCodeReader reader;
    PDFCreator creator;

    public PDFProcessor(PDFSplitter splitter, QRCodeReader reader, PDFCreator creator) {
        this.splitter = splitter;
        this.reader = reader;
        this.creator = creator;
    }

    public ArrayList<ExamPaper> processPDF(InputStream pdfStream) throws IOException, DocumentException, NotFoundException, COSVisitorException {
        List<BufferedImage> pdfpapers = new ArrayList<>();
        pdfpapers = splitPDFs(pdfStream);

        return createPapers(pdfpapers);

//        ArrayList<ExamPaper> papers = new ArrayList<>();
//        try {
//            List<BufferedImage> splitPdf = splitter.splitPdf(pdfStream);
//            ExamPaper paper = new ExamPaper();
//            for (int i = 0; i < splitPdf.size(); i++) {
//                if (i % 2 == 0 && i != 0) {
//                    if (paper.getQRCodeString() != null) {
//                        papers.add(paper);
//                    } else {
//                        // TODO no qr code
//                    }
//                    paper = new ExamPaper();
//                }
//                try {
//                    Result result = reader.readQRCode(splitPdf.get(i));
//                    paper.setQRCodeString(result.getText());
//                } catch (NotFoundException ex) {
//                    // TODO no qr code - not needed
//                }
//            }
//        } catch (IOException ex) {
//            // TODO wrong document type
//        } catch (DocumentException ex) {
//            // TODO odd number of pages
//        }
//        return papers;
    }

    public List<BufferedImage> splitPDFs(InputStream pdfStream) throws IOException, DocumentException {
        List<BufferedImage> splitPdf = splitter.splitPdf(pdfStream);
        return splitPdf;
    }

    public ArrayList<ExamPaper> createPapers(List<BufferedImage> splittedPDFs) throws NotFoundException, IOException, COSVisitorException {
        ArrayList<ExamPaper> papers = new ArrayList<>();
        List<BufferedImage> twoPages;

        for (int i = 0; i < splittedPDFs.size(); i++) {
            if (i % 2 == 0 && i != 0) {
                twoPages = new ArrayList<>();
                twoPages.add(splittedPDFs.get(i - 1));
                twoPages.add(splittedPDFs.get(i));
                ExamPaper paper = new ExamPaper();
                paper.setQRCodeString(readQRcodes(twoPages).getText());
                paper.setPdfStream(creator.createPDF(twoPages));
                papers.add(paper);
            }
        }
        return papers;
    }

    public Result readQRcodes(List<BufferedImage> pages) throws NotFoundException, COSVisitorException {
        Result result = null;

        for (BufferedImage bufferedImage : pages) {
            result = reader.readQRCode(bufferedImage);
        }
        return result;
    }
}
