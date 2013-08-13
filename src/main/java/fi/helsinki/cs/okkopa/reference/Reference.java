package fi.helsinki.cs.okkopa.reference;

import fi.helsinki.cs.okkopa.Settings;

public class Reference {
    private final ReferenceNumber number;
    private final Integer size;
    private final ReferenceString letters;
    
    /**
     *
     * @param settings Settings that are loaded from a settings file.
     */
    public Reference(Settings settings) {
        this.size = Integer.valueOf(settings.getSettings().getProperty("reference.anonymous.size"));
        
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
