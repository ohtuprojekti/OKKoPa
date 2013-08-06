package fi.helsinki.cs.okkopa.reference;

import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;

public class ReferenceString {

    private final int size;
    private final Random randomGenerator;
    private String string;

    public ReferenceString(int size) {
        this.size = size;
        randomGenerator = new Random();
    }

    public String getReference() {
        string = "" + (1 + randomGenerator.nextInt(9)) + this.getRandomPart();
        return string + this.getCheckPart(string);
    }

    private String getRandomPart() {
        return RandomStringUtils.randomAlphanumeric(size - 2).toLowerCase();
    }

    private String getCheckPart(String string) {
        int sum = 0;
        for (int i = 0; i < string.length(); i++) {
            sum += string.charAt(i);
        }
        if (sum % 36 < 10) {
            return "" + sum % 36;
        } else {
            return Character.toString((char) (87 + sum % 36));
        }
    }

    public boolean checkReference(String reference) {
        String subString = reference.substring(0, reference.length() - 1);
        if (reference.equals(subString + this.getCheckPart(subString))) {
            return true;
        } else {
            return false;
        }
    }
}
