package stories

import fi.helsinki.cs.okkopa.qr.QRCodeReader

description "QR code reading"
 
scenario "Reading an image file with only QR code", {
 
    given "an image file",{
        reader = new QRCodeReader()
    }
 
    when "QR code is read", {
    }
 
    then "the result should be ??", {
    }
}