/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kingbar
 */
public class Authenticate
{
    
    private ArrayList<PasswordEntity> passwordEntities;
    private boolean result;
    private String authenticationKey;
    
    
        
    public Authenticate()
    {
        this.result = false;
        this.passwordEntities = new ArrayList();
        this.authenticationKey = "";
        
        if( File.userFileExist() )
        {
            this.signIn();    
        }
        else
        {
            this.signUp(); 
        }
    }
    
    public boolean getResult()
    {
        return this.result;
    }
    
    public ArrayList<PasswordEntity> getPasswordEntities ()
    {
        return this.passwordEntities;
    }
    
    public String getAuthenticationKey()
    {
        return this.authenticationKey;
    }
    
    
    
    public void signIn()
    {
        System.out.println("Welcome Back.");
        int[] hackProofData = File.readHackProofData();
        int numberOfAttempt = hackProofData[0];
        int attemptWaitTime = hackProofData[1];
        
        boolean successfullLogin = false;   

        while(!successfullLogin)
        {
            String password;
            String error;
            if( numberOfAttempt < 4 )
            {
                password = Keyboard.getStringInput("Please Enter Your Password: ");
            }
            else
            {
                password = "";
                numberOfAttempt--;
                attemptWaitTime = attemptWaitTime /2;
                File.writeDataToFile("hackProofData.txt", numberOfAttempt+","+attemptWaitTime);
            }
            
            error = Validate.userApplicationPassword(password);
            
            while( error.length() != 0 )
            {
                numberOfAttempt++;
                File.writeDataToFile("hackProofData.txt", numberOfAttempt+","+attemptWaitTime);
                
                System.out.println("Invalid Login.");
                
                if( numberOfAttempt > 3 )
                {
                    attemptWaitTime = attemptWaitTime * 2;
                    File.writeDataToFile("hackProofData.txt", numberOfAttempt+","+attemptWaitTime);
                    
                    System.out.println("Please Wait "+attemptWaitTime+" Minutes Before You Try Again");

                    try
                    {
                         //Thread.sleep(attemptWaitTime*1000);
                         TimeUnit.MINUTES.sleep(attemptWaitTime);
                    }
                    catch( InterruptedException e )
                    {
                        new Exception("Sleep Interrupted");
                    }
                }
                
                password = Keyboard.getStringInput("Please Enter Your Password: ");
                error = Validate.userApplicationPassword(password);
            }
            
            //GET THE USER SALT AND HASH SINCE THE SYSTEM ITS ONLY FOR ONE USER
            String[] authenticationData = File.readAuthenticationData();
            Password passwordObject = new Password(password, authenticationData[0]);

            if( passwordObject.matchesHash(authenticationData[2]) )
            {
                System.out.println("Login Successful.\n");
                //RESET HACKPROOF DATA
                File.writeDataToFile("hackProofData.txt", "1,1");

                String passwordStoreSalt = authenticationData[1];               
                Password newPassword = new Password( password, passwordStoreSalt );
                String newPasswordHash = newPassword.generateHash();
                
                //RESET THE Pasword VALUE IN MEMORY
                password = null;
                
                //save the hash of password and saltOfPasswordStore which can be used to for the AES encryption / decryption.
                this.authenticationKey = newPasswordHash; 
                

                //GET THE ENCRYPTED CIPHERTEXT FROM FILE AND DECRYPT USING THE KEY
                String cipherText = File.readEncryptedPasswordStoreData();
                if( cipherText.length() != 0 )
                {
                    String plainText = Cipher.decryptString(cipherText, newPasswordHash);
                    ArrayList<PasswordEntity> passwordEntities = File.deserializePasswordStore(plainText);
                    this.passwordEntities = passwordEntities;
                }
                               
                this.result = true;                
                successfullLogin = true;
            }
            else
            {
                System.out.println("Invalid Login.");
                numberOfAttempt++;
                File.writeDataToFile("hackProofData.txt", numberOfAttempt+","+attemptWaitTime);
                if( numberOfAttempt > 3 )
                {
                    attemptWaitTime = attemptWaitTime * 2;
                    File.writeDataToFile("hackProofData.txt", numberOfAttempt+","+attemptWaitTime);
                    
                    System.out.println("Please Wait "+attemptWaitTime+" Minutes Before You Try Again");

                    try
                    {
                         //Thread.sleep(attemptWaitTime*1000);
                         TimeUnit.MINUTES.sleep(attemptWaitTime);
                    }
                    catch( InterruptedException e )
                    {
                        new Exception("Sleep Interrupted");
                    }
                }


            }
        }
    }
    
    public void signUp()
    {
        
        System.out.println("New User, You're Welcome.");
        String password = Keyboard.getStringInput("Please Choose A Password: ");
        String passwordConfirm = Keyboard.getStringInput("Re-enter The Password: ");

        
        String error = Validate.userApplicationPassword(password);
        while( !password.equals(passwordConfirm) || error.length() !=0 )
        {
            if( !password.equals(passwordConfirm) )
            {
                System.out.println("Password Did Not Match");
            }
            if( error.length() !=0 )
            {
                System.out.println(error);
            }
            password = Keyboard.getStringInput("\nPlease Choose A Password: ");
            passwordConfirm = Keyboard.getStringInput("Re-enter The Password: ");
            error = Validate.userApplicationPassword(password);
            
        }
        String saltOne = Password.generateRandomSalt();
        String saltTwo = Password.generateRandomSalt();
        Password accountPassword = new Password(password, saltOne);
        String hash = accountPassword.generateHash();
        
        this.authenticationKey = new Password(password, saltTwo).generateHash();      
        
        //RESET THE PASSWORD IN MEMORY
        password = null;
        passwordConfirm = null;
        
        File.writeDataToFile("user.txt", saltOne+" "+saltTwo+" "+hash);
        File.writeDataToFile("passwordStore.txt", "");
        File.writeDataToFile("hackProofData.txt", "0,1");
        System.out.println("Sign-Up Successfull\n");
        
        
        this.result = true;
        
    }
    
}
