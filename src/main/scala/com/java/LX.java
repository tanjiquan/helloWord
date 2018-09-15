package com.java;

import java.util.Scanner;

// 菱形输出
public class LX {

    public static void main(String[] args) {
        int num;
        Scanner input = new Scanner(System.in);
        System.out.print("请输入菱形的行数，菱形要为奇数");
        do{
            num = input.nextInt();
            if ( num % 2 == 0 ) {
                System.out.println("请输入奇数");
            }else {

                int upper = ( num + 1 ) / 2;
                int down = num / 2;
                //上面部分
                for( int i = 0; i < upper ; i++ ){
                    for( int j = 0; j< upper-i-1; j++ ){
                        System.out.print(" ");
                    }
                    for( int k = 0; k < i*2+1; k++){
                        System.out.print("*");
                    }
                    System.out.println();
                }
                //下面部分
                for(int i = 0; i < down; i++ ){
                    for( int j = 0; j < i+1 ; j++ ){
                        System.out.print(" ");
                    }
                    for( int k = 0; k < 2*down-1-2*i; k++ ){
                        System.out.print("*");
                    }
                    System.out.println();
                }

            }
            System.out.print("请输入菱形的行数，菱形要为奇数");
        }while ( input.hasNextInt() );

    }
}
