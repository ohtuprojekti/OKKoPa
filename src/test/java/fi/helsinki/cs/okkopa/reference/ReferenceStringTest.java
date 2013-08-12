package fi.helsinki.cs.okkopa.reference;

import fi.helsinki.cs.okkopa.reference.ReferenceString;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReferenceStringTest {

    private ReferenceString reference;

    @Before
    public void setUp() {
        this.reference = new ReferenceString(6);
    }

    @Test
    public void testCheckReferenceNumber() {
        String tempReference;
        for (int i = 0; i < 1000; i++) {
            tempReference = reference.getReference();
            assertEquals(true, reference.checkReference(tempReference));
        }

        assertEquals(false, reference.checkReference("6xy0td"));
    }
}