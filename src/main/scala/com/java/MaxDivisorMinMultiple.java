package com.java;

import java.util.Scanner;

public class MaxDivisorMinMultiple {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Enter m, n: ");

        do {
            int m, n;

            m = in.nextInt();
            n = in.nextInt();

            int commonDivisor = MaxDivisorMinMultiple.maxCommonDivisor(m, n);

            int commonMultiple = MaxDivisorMinMultiple.minCommonMultiple(m, n);

            System.out.println(" m 和 n 的最大公约数: " + commonDivisor);

            System.out.println(" m 和 n 的最小公倍数: " + commonMultiple);

            System.out.println("Enter m, n: ");
        } while (in.hasNextInt());
    }
    //计算两个正整数m和n的最大公约数
    // 最大公约数不会大于两个正整数之中的最小者
    public static int maxCommonDivisor( int m, int n ){
        int commonDivisor;
        if( m < n ){
            commonDivisor = m;
        } else {
            commonDivisor = n;
        }
        while ( m % commonDivisor != 0 || n % commonDivisor != 0 ){
            commonDivisor --;
        }
        return commonDivisor;
    }

    // 计算两个正整数m 和 n 的最小公倍数
    // 最小公倍数不会小于两个正整数中的较大者
    public static int minCommonMultiple( int m, int n ){
        int commonMultiple;
        if( m > n ){
            commonMultiple = m;
        } else {
            commonMultiple = n;
        }
        while ( commonMultiple % m != 0 || commonMultiple % n != 0 ){
            commonMultiple ++;
        }
        return commonMultiple;
    }

}
