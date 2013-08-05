package ViiteNumero;

import java.util.Random;

public class ViiteNumero implements Viite {

    private final Random randomGenerator;
    private int number;
    private boolean reset;
    private int place;
    private StringBuilder sb;
    private int tarkistus;
    private int random;
    private String string;

    public ViiteNumero() {
        randomGenerator = new Random();
    }

    @Override
    public int getReferenceNumber() {
        return this.getReferenceNumber(this.randomEightNumber());
    }
    
    private int getReferenceNumber(int random) {
        this.random = random;
        string = Integer.toString(this.random);
        
        this.generateCheck();
        this.concatenate();
        
        return Integer.valueOf(sb.toString());
    }
    
    @Override
    public boolean checkReferenceNumber(int number) {
        string = Integer.toString(number);
        string = string.substring(0, string.length() -1);
        
        if(this.getReferenceNumber(Integer.valueOf(string)) == number) {
            return true;
        }
        return false;
    }

    private int randomEightNumber() {
        return 10000000 + randomGenerator.nextInt(89999999);
    }

    private void resetX() {
        reset = true;
    }

    private int getX() {
        int[] array;
        array = new int[3];
        array[0] = 7;
        array[1] = 3;
        array[2] = 1;

        if (reset || place == 2) {
            place = 0;
            reset = false;
        } else {
            place++;
        }
        return array[place];
    }

    private void concatenate() {
        sb = new StringBuilder(9);
        sb.append(random);
        
        tarkistus = Math.abs(number % 10 - 10);
        if (tarkistus != 10) {
            sb.append(tarkistus);
        } else {
            sb.append(0);
        }
    }

    private void generateCheck() {
        this.resetX();
        number = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            number += this.getX() * Character.getNumericValue(string.charAt(i));
        }
    }
}
