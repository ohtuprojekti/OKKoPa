package fi.helsinki.cs.okkopa.qr;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

public class PDFSplitter {

    public List<ExamPaper> splitPdf(InputStream pdfDocument) throws IOException, RendererException, DocumentException {
        // load PDF document
        PDFDocument document = new PDFDocument();
        document.load(pdfDocument);

        // create renderer
        SimpleRenderer renderer = new SimpleRenderer();

        // set resolution (in DPI)
        renderer.setResolution(300);

        // split to images
        List<Image> images = renderer.render(document);

        ArrayList<ExamPaper> papers = new ArrayList<>();

        // split images to exam papers: two per exam paper (two-sided)
        for (int i = 0; i < images.size(); i++) {
            if (i % 2 == 0) {
                ExamPaper paper = new ExamPaper();
                papers.add(paper);
            }
            // add to last exam paper
            papers.get(papers.size() - 1).addPage(images.get(i));
        }

        return papers;
    }
}
