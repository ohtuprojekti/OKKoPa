package stories

import fi.helsinki.cs.okkopa.qr.*

description "PDF processing test"
 
scenario "Reading an image file with only QR code", {
 
    given "an image file",{
        PDFProcessor processor = new PDFProcessorImpl();
    }
 
    when "QR code is read", {
    }
 
    then "the result should be ??", {
    }
}