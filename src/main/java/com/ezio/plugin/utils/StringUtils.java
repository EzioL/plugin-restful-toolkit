package com.ezio.plugin.utils;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/6
 */
public class StringUtils {

    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
}
