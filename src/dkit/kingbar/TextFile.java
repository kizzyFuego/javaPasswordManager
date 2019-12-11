package dkit.kingbar;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Kingbar
 */
public class TextFile {
 
    private static final Charset ENCODING       = StandardCharsets.UTF_8;
    private static final String USER_FILE_PATH  = "user.txt";
    private static final String STORE_FILE_PATH = "passwordStore.txt";
    
    public static boolean checkFileExists(String fileName){
        File tempDir = new File(fileName);
        boolean exists = tempDir.exists();
        
        return exists;
    }
    
    public static String getMasterHash(){
        
        try (Scanner inFile = new Scanner(new FileReader(USER_FILE_PATH))){
            
            String oldHash = inFile.nextLine();
        
            if(oldHash.isEmpty()){
                throw new TextFileException("Line 1 in " + USER_FILE_PATH + " is empty.");
            }
            
            return oldHash;
        }
        catch(FileNotFoundException e){
            throw new TextFileException("Cannot read required file " + USER_FILE_PATH);
        }
        catch(NoSuchElementException e){
            throw new TextFileException("Could not read from line 1 from " + USER_FILE_PATH);
        }
    }
    
    public static void setMasterHash(String newHash) {
        
        // The boolean indicates if it will append, as its false this means it will overwrite
        try (FileWriter fileWriter = new FileWriter(USER_FILE_PATH, false)){
            
            fileWriter.write(newHash);
            fileWriter.close();
        }
        catch(FileNotFoundException e){
            throw new TextFileException("Cannot read required file " + USER_FILE_PATH);
        }
        catch(IOException e){
            throw new TextFileException("An IOException was thrown trying to write to " + USER_FILE_PATH);
        }
            
    }
    
    public static List<String> getPasswordStore(){
        List<String> cipherStore = new ArrayList<>();
        
        return cipherStore;
    }
    
    public static void updatePasswordStore(){
        
    }
    
}
