/*
    Bryson Gundry
    Programming Assignment 2 - multiply.java
    7/22/2019
*/

import java.util.*;
import java.util.Scanner;

//This program shows the steps for 8-bit by 8-bit multiplication. 
//The user must input two values, separated by a space in between, for the program to be run successfully.

public class multiply {

    //takes integer and converts it into an 8 bit binary number
    public static String intToBinary(int toBinary) {
        String temp = Integer.toBinaryString(toBinary);

        while(temp.length() < 8){
            temp = "0" + temp;
        }

        return temp;
    }

    //adds two binary numbers together
    public static String addBinary(String firstNumber, String secondNumber){
        int first = firstNumber.length() - 1;
        int second = secondNumber.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carry = 0;

        while (first >= 0 || second >= 0) {
            int sum = carry;

            if (first >= 0) {
                sum += firstNumber.charAt(first) - '0';
                first--;
            }

            if (second >= 0) {
                sum += secondNumber.charAt(second) - '0';
                second--;
            }

            carry = sum >> 1;
            sum = sum & 1;
            sb.append(sum == 0 ? '0' : '1');
        }

        if (carry > 0)
            sb.append('1');

        sb.reverse();
        return String.valueOf(sb);
    }

    public static void main(String[] args){
        int multiplicand;
        int multiplier;
        String multiplicandHolder;
        String multiplierHolder;
        String tempHolder;
        String second;

	//reading in user input from Scanner
	Scanner scanner = new Scanner(System.in); 
	System.out.println("Please input two numbers, separated by a space.");
	multiplicand = scanner.nextInt();
	multiplier = scanner.nextInt();

        System.out.printf("Multiplicand: " + multiplicand);

        System.out.printf("\nMultiplier: " + multiplier + "\n");

	//setting conditions for input by user
        if(multiplicand < 0 || multiplicand > 255 || multiplier < 0 || multiplier > 255){
            System.out.println("Numbers must be greater than or equal to 0 and less than or equal to 255");
            System.exit(1);
        }

	//begin of statements to print to user, showing steps of the multiplication and final answer
	//and formatting of the output for convenience of reading.
        int carry = 0;
        String acc = intToBinary(0);
        System.out.println("\nc and acc set to 0");

        multiplicandHolder = intToBinary(multiplicand);
        multiplierHolder = intToBinary(multiplier);

        System.out.println("mq set to multiplier    =  " + String.format("%3s", multiplier) + " and " +
                          multiplierHolder + " binary");
        System.out.println("mdr set to multiplicand =  " + String.format("%3s", multiplicand) + " and " +
                          multiplicandHolder + " binary");

        System.out.println("---------------------------------------------------");
        System.out.println("step 1:   " + carry + " " + acc + " " + multiplierHolder);
        System.out.printf("        +   " + multiplicandHolder + "        ");

        if(multiplierHolder.charAt(7) == '1'){

            System.out.printf("^ add based on lsb=1\n");
            System.out.println("          ----------");
            System.out.println("          " + carry + " " + multiplicandHolder + " " + multiplierHolder);
            tempHolder = carry + multiplicandHolder + multiplierHolder;
        }

        else{

            System.out.printf("^ no add based on lsb=0\n");
            System.out.println("          ----------");
            System.out.println("          " + carry + " " + acc + " " + multiplierHolder);
            tempHolder = carry + acc + multiplierHolder;

        }

        System.out.println("       >>                     shift right");
        tempHolder = "0" + tempHolder.substring(0, 16);
        System.out.println("          " + tempHolder.substring(0, 1) + " " + tempHolder.substring(1, 9)
                + " " + tempHolder.substring(9, 17));

        for(int i = 2; i < 9; i++){

            System.out.println("---------------------------------------------------");
            System.out.println("step " + i + ":   " + tempHolder.substring(0, 1) + " " + tempHolder.substring(1, 9)
                    + " " + tempHolder.substring(9, 17));

            if(tempHolder.charAt(16) == '1'){

                System.out.printf("        +   " + multiplicandHolder + "        ");
                System.out.printf("^ add based on lsb=1\n");
                second = addBinary(multiplicandHolder, tempHolder.substring(0, 9)) + tempHolder.substring(9, 17);
            }

            else{

                System.out.printf("        +   " + intToBinary(0) + "        ");
                System.out.printf("^ no add based on lsb=0\n");
                second = tempHolder;
            }

            System.out.println("          ----------");
            System.out.println("          " + second.substring(0, 1) + " " + second.substring(1, 9)
                    + " " + second.substring(9, 17));
            tempHolder = "0" + second.substring(0, 16);
            System.out.println("       >>                     shift right");
            System.out.println("          " + tempHolder.substring(0, 1) + " " + tempHolder.substring(1, 9)
                    + " " + tempHolder.substring(9, 17));
        }

        System.out.println("---------------------------------------------------");
        System.out.println("check:                 binary   decimal");
        System.out.println("                     " + multiplicandHolder + "    " + String.format("%6s", multiplicand));
        System.out.println("           x         " + multiplierHolder + "  x " + String.format("%6s", multiplier));
        System.out.println("             ----------------    ------");
        System.out.println("             " + String.format("%16s",
                Integer.toBinaryString((multiplicand * multiplier))).replace(' ', '0') + "   " +
                            String.format("%7s",(multiplicand * multiplier)));
    }

}
