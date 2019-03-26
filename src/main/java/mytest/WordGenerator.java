package mytest;

import java.util.Random;

public class WordGenerator {

    private static final String values = "abcdefghijklmnopqrstuvwxyz01234567890";

    public static String genWord(int length){

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i<length; i++){

            buffer.append(values.charAt(new Random(System.nanoTime()).nextInt(values.length())));

        }
        return buffer.toString();
    }

}
