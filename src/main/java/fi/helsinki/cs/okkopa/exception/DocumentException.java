package fi.helsinki.cs.okkopa.exception;

public class DocumentException extends Exception {

    public DocumentException() {
        super();
    }

    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentException(Throwable cause) {
        super(cause);
    }
}
