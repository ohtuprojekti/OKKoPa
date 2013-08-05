package ViiteNumero;

public interface Viite {

    /**
     *
     * @param number refence number.
     * @return true, if chacknumber was correct. False if not.
     */
    boolean checkReferenceNumber(int number);

    /**
     *
     * @return random reference number;
     */
    int getReferenceNumber();
    
}
