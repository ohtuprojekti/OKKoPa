package fi.helsinki.cs.okkopa.reference;

import fi.helsinki.cs.okkopa.main.Settings;

public class Reference {

    private final ReferenceNumber number;
    private final ReferenceString letters;

    public Reference(Settings settings) {
        Integer size = Integer.valueOf(settings.getProperty("reference.anonymous.size"));

        this.number = new ReferenceNumber(size);
        this.letters = new ReferenceString(size);
    }

    public boolean checkReferenceNumber(int number) {
        return this.number.checkReferenceNumber(number);
    }

    public int getReferenceNumber() {
        return this.number.getReferenceNumber();
    }

    public String getReference() {
        return this.letters.getReference();
    }

    public boolean checkReference(String reference) {
        return this.letters.checkReference(reference);
    }
}
