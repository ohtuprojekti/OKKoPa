package fi.helsinki.cs.okkopa.qr;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFSplitter {

    public List<ExamPaper> splitPdf(InputStream pdfStream) throws IOException, Exception {
        PDDocument document = PDDocument.load(pdfStream);
        if (document.getNumberOfPages() % 2 != 0) {

//      TO DO Exception      

            throw new Exception("Wrong number of pages");
        }
        List<PDPage> pages = document.getDocumentCatalog().getAllPages();

        ArrayList<ExamPaper> papers = new ArrayList<>();

        // split images to exam papers: two per exam paper (two-sided)
        for (int i = 0; i < pages.size(); i++) {
            if (i % 2 == 0) {
                ExamPaper paper = new ExamPaper();
                papers.add(paper);
            }
            // add to last exam paper
            papers.get(papers.size() - 1).addPage(pages.get(i).convertToImage());
        }
        document.close();
        return papers;
    }
}
