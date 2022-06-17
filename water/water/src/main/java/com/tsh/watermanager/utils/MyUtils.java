package com.tsh.watermanager.utils;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/4/19 15:53
 * FileName: Tashany
 * Description:
 */


import java.math.BigInteger;

public class MyUtils {

    /**
     * 合并数组
     *
     * @param firstArray  第一个数组
     * @param secondArray 第二个数组
     * @return 合并后的数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            if (secondArray != null)
                return secondArray;
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
        return bytes;
    }

    public static void main(String[] args) {
        BigInteger bigint=new BigInteger("0403", 16);
        int numb=bigint.intValue();
        System.out.println(numb/100.00);
    }
}