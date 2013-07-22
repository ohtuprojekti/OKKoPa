package stories

import fi.helsinki.cs.okkopa.qr.QRReader

description "QR code reading"
 
scenario "Reading an image file with only QR code", {
 
    given "an image file",{
        reader = new QRReader()
    }
 
    when "QR code is read", {
    }
 
    then "the result should be", {
        1.shouldBe 2
    }
}