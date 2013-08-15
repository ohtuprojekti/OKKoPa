package fi.helsinki.cs.okkopa.reference;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ReferenceNumberTest {

    private ReferenceNumber random;

    @Before
    public void setUp() {
        random = new ReferenceNumber(9);
    }

    @Test
    public void testGenerateReferenceNumber() {
        for (int i = 0; i < 1000; i++) {
            int luku = random.getReferenceNumber();
            assertEquals(true, random.checkReferenceNumber(luku));
        }
        assertEquals(false, random.checkReferenceNumber(134576547));
    }
}