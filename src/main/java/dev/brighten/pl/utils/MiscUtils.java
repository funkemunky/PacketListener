package dev.brighten.pl.utils;

import java.util.List;

public class MiscUtils {

    public static boolean isArrayEqual(Object[] arrayOne, Object[] arrayTwo) {
        if(arrayOne.length != arrayTwo.length) return false;

        boolean equal = true;
        for (int i = 0; i < arrayOne.length; i++) {
            Object one = arrayOne[i], two = arrayTwo[i];

            if(!one.equals(two)) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}
