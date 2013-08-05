package ViiteNumero;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ViiteNumeroTest {
    private ViiteNumero random;
    
    @Before
    public void setUp() {
        random = new ViiteNumero();
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