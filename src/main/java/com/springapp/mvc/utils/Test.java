package com.springapp.mvc.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aashish on 20/10/16.
 */
public class Test {

    public static void main(String []args) {
     System.out.println(convertStringToInt("1234"));
     System.out.println(convertStringToInt("abc0d"));
     System.out.println(convertStringToInt("13O57"));

    }

    private static int convertStringToInt(String str) {
        int i = 0;
        for (char ch : str.toCharArray()) {

            if (ch == '~' || ch == '`' || ch == '!') {
                i =i*10+ 1;
            } else if (ch == '@') {
                i =i*10+ 2;
            } else if (ch == '-' || ch == '_') {
                i =i*10+ 3;
            } else if (ch == '/') {
                i =i*10+ 4;
            }else if(ch == 'O' || ch =='o') {
                i =i*10+0;
            }else if (ch >= 'a' && ch <= 'z') {
                i =i*10+ (ch - 'a'+1);
            } else if (ch >= 'A' && ch <= 'Z') {
                i =i*10+ (ch - 'A'+1);
            } else if (ch >= '0' && ch <= '9') {
                i =i*10+ (ch - '0');
            }
        }
        return i;
    }
}
