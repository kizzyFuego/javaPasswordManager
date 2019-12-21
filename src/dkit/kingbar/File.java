/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.time.LocalDateTime;


/**
 *
 * @author kingbar
 */
public class File
{
    public static boolean userFileExist( )
    {
        String outputFileName = "user.txt";
        java.io.File file = new java.io.File (outputFileName);      
        return file.exists();         
    }
    
    
    public static String[] readAuthenticationData()
    {
        String fileName = "user.txt";
        Scanner keyboard = new Scanner(System.in);
        
        String[] authenticationData = new String[3];
      
        //Need to consider case of data already in ownerList?
        try (Scanner in = new Scanner(new java.io.File(fileName)))
        {
            // set the delimiter to be a comma character "," 
            // a carriage-return, or newline        
            //in.useDelimiter("[,\n]+");
            in.useDelimiter(" ");
            authenticationData[0] = in.next();
            authenticationData[1] = in.next();
            authenticationData[2] = in.next();        
        }
        catch (FileNotFoundException exception)
        {   
            throw new FileException("System File Could Not Be Found.");                     
        }
        catch (NoSuchElementException e)
        {
            throw new FileException("System File Is Corrupt.");
        }
        catch (IllegalStateException e)
        {
            throw new FileException("Scanner Is Closed.");
        }
        
        return authenticationData;
    }
    
    
    public static String readEncryptedPasswordStoreData()
    {
        String fileName = "passwordStore.txt";
        
        String result = "";
      
        //Need to consider case of data already in ownerList?
        try (Scanner in = new Scanner(new java.io.File(fileName)))
        {
            if( in.hasNext() )
            {
                result = in.next();
            }
            
        }
        catch (FileNotFoundException exception)
        {   
            throw new FileException("File Could Not Be Found.");                     
        }
        catch (NoSuchElementException e)
        {
            throw new FileException("System File Is Corrupt.");
        }
        catch (IllegalStateException e)
        {
            throw new FileException("Scanner Is Closed.");
        }
        
        return result;
    }
      
    
    public static ArrayList<PasswordEntity> deserializePasswordStore(String serializeData)
    {
        //String fileName = "passwordStore.txt";
        Scanner in = new Scanner(serializeData);
        
        
        ArrayList<PasswordEntity> passwordEntityList = new ArrayList();
      
        //Need to consider case of data already in ownerList?
        try
        {
            //Use The Scanner Below If I Was To Read The Data From A File. 
            //Scanner inn = new Scanner(new java.io.File(fileName) );
            
            // set the delimiter to be a comma character "," 
            // a carriage-return, or newline        
            in.useDelimiter("[\\|\n]+");
            
           
            while (in.hasNext())
            {
                int id = in.nextInt();
                String title = in.next();
                String website = in.next();
                String password = in.next();
                LocalDateTime lastUpdated = LocalDateTime.parse(in.next());
                
                PasswordEntity passwordEntity = new PasswordEntity(id, title, website, password, lastUpdated);
                
                passwordEntityList.add(passwordEntity);
                
                if (in.hasNextLine())
                {
                    in.nextLine();
                }

            }
            
        }
        catch (NoSuchElementException e)
        {
            throw new FileException("System File Is Corrupt.");
        }
        
        return passwordEntityList;
    }
    
    
    public static void writeDataToFile( String outputFileName, String data)
    {
        //String outputFileName = "user.txt";
        //String outputFileName = "passwordStore.txt";
        
        java.io.File file = new java.io.File (outputFileName);
        if( !file.exists() )
        {
            createFile(outputFileName);
        }
        
        try (PrintWriter writer = new PrintWriter(outputFileName);)
        {
            writer.print(data);
        } catch (FileNotFoundException e)
        {
            throw new FileException("File Could Not Be Found.");
        }
    }
    
    public static int[] readHackProofData()
    {
        String fileName = "hackProofData.txt";
        int[] data = new int[2];    
        try
        {
            Scanner in = new Scanner(new java.io.File(fileName) );
            
            // set the delimiter to be a comma character "," 
            // a carriage-return, or newline        
            in.useDelimiter("[,\n]+");
            data[0] = in.nextInt();
            data[1] = in.nextInt();             
        }
        catch( FileNotFoundException e )
        {
            throw new FileException("System File Could Not Be Found.");
        }
        catch (NoSuchElementException e)
        {
            throw new FileException("System File Is Corrupt.");
        }
        return data;
        
    }
    
    public static boolean createFile( String outputFileName )
    {  
        boolean result = false;
        try
        {
            java.io.File file = new java.io.File (outputFileName);
            result = file.createNewFile();    
        }
        catch (SecurityException e)
        {
            throw new FileException("File Permission Issue.");
        }
        catch (IOException e)
        {
            throw new FileException("IO Exception.");
        }
        
        return result;
    }
    
    
    //the writing funtion needs some fixing
//    public static void writeToFile( String outputFileName, String data )
//    {
//        try
//        {
//            java.io.File file = new java.io.File (outputFileName);
//            if( !file.exists() )
//            {
//                createFile(outputFileName);
//            }
//            FileWriter myWriter = new FileWriter(outputFileName);
//            System.out.println("Data to write= "+data);
//            myWriter.write(data);
//        }
//        catch (IOException e)
//        {
//            System.out.println("An Error Occurred");
//        }
//    }
    
   
}
