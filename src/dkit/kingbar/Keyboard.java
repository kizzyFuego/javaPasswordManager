/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;
import java.util.Scanner;

/**
 *
 * @author kingbar
 */
public class Keyboard
{
    public static String getStringInput(String displayInstruction)
    {
        Scanner keyboard = new Scanner(System.in);
        String input = null;
        System.out.print(displayInstruction);

        if (keyboard.hasNextLine())
        {
            input = keyboard.nextLine();
            System.out.println("\n");
        }
        return input;
    }
    
    public static int getIntInput(String displayInstruction)
    {
        Scanner keyboard = new Scanner(System.in);
        String inputLine;
        boolean integerEntered = false;
        int input = 0;
        while(!integerEntered)
        {
            try
            {
                System.out.print(displayInstruction);
                inputLine = keyboard.nextLine();
                input = Integer.parseInt(inputLine);
                System.out.print("\n");
                integerEntered = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("\nInvalid Number.\n");
                integerEntered = false;          
            }
        }
        
        return input;
    }
    
    public static int getIntInputWithMin(String displayInstruction, int min)
    {     
        Scanner keyboard = new Scanner(System.in);
        String inputLine;
        boolean integerEntered = false;
        int input = 0;
        while(!integerEntered)
        {
            try
            {
                System.out.print(displayInstruction);
                inputLine = keyboard.nextLine();
                input = Integer.parseInt(inputLine);
                System.out.print("\n");

                while (input < min)
                {
                    System.out.println("\nMinimum Number is "+min+".\n");
                    System.out.print("\n" + displayInstruction);
                    inputLine = keyboard.nextLine();
                    input = Integer.parseInt(inputLine);
                }
                System.out.print("\n");
                integerEntered = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("\nInvalid Number.\n");
                integerEntered = false;          
            }
        }
        
        return input;
    }
    
    public static int getIntInputWithMinAndMax(String displayInstruction, int min, int max)
    {     
        Scanner keyboard = new Scanner(System.in);
        String inputLine;
        boolean integerEntered = false;
        int input = 0;
        while(!integerEntered)
        {
            try
            {
                System.out.print(displayInstruction);
                inputLine = keyboard.nextLine();
                input = Integer.parseInt(inputLine);
                System.out.print("\n");

                while (input < min || input > max)
                {
                    System.out.println("\nInvalid Number.\n");
                    System.out.print("\n" + displayInstruction);
                    inputLine = keyboard.nextLine();
                    input = Integer.parseInt(inputLine);
                }
                System.out.print("\n");
                integerEntered = true; 
            }
            catch (NumberFormatException e)
            {
                System.out.println("\nInvalid Number.\n");
                integerEntered = false;          
            }
        }
        
        return input;
    }
    
}
