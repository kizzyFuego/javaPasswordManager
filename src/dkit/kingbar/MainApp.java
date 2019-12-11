package dkit.kingbar;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author  Kingbar
 */
public class MainApp {
    
    // Console Output Messages
    private static final String GOODBYE_MESSAGE     = "Goodbye.";
    private static final String ENTER_TO_CONTINUE   = "Press enter to continue...";
    private static final String PASSWORD_INCORRECT  = "Incorrect Password";
    
    // Configuration Values
    private static final String TABLE_SALT      = "eoHB4pKiwBfx2atCBOsGgwEAdjSPtQ6xdS8+ekcwGtw=";
    private static final String WEAK_PASSWORD   = "Your password must be 10 characters long,"
            + "\nand contain one or more: uppercase letter, lowercase letter, "
            + "\ninteger and special character (!@#$%^&*)";
    private static final String STRONG_REGEX    = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=.{10,})";
    private static final String TEST_REGEX      = "^\\w{10,}";
    // The above regex was taken from;
    // https://www.thepolyglotdeveloper.com/2015/05/use-regex-to-test-password-strength-in-javascript/
    
    // Instance properties, Reference; Shane Gavins Sample Solution for CA1
    private boolean running;
    private Scanner keyboard;
    
    public MainApp(){
        this.keyboard = new Scanner(System.in);
        this.run();
    }
    
    private void run(){
        
        this.running = true;
        
        while(this.running){
            if(TextFile.checkFileExists("user.txt")){
                signIn();
            }
            else{
                signUp();
            }
            this.stop();
        }
        
        
        
    }
    
    public void signUp(){
        System.out.println("Welcome new user");
        String passPlain = null;
        boolean weakPass = true;
        
        while(weakPass){
            
            passPlain = enterPassword();
            // @HERE
            Pattern pattern = Pattern.compile(TEST_REGEX);
            Matcher matcher = pattern.matcher(passPlain);
            
            if(matcher.matches()){
            weakPass = false;
            }
            else{
                System.out.println(WEAK_PASSWORD);
                
            }
        }
        
        //String salt = Password.generateRandomSalt();
        String passHash = new Password(passPlain, TABLE_SALT).generateHash();
        
        TextFile.setMasterHash(passHash);
        
    }
    
    public void signIn(){
        System.out.println("Please enter your password");
        String passEntry = enterPassword();
        String passEntryHash = new Password(passEntry, TABLE_SALT).generateHash();
        
        String passHash = TextFile.getMasterHash();
        if(passEntryHash.equals(passHash)){
            System.out.println("ITS YOU WELCOME BACK");
        }
        else{
            System.out.println(PASSWORD_INCORRECT);
        }
        
    }
    
    
    public String enterPassword(){
        String pass;
            System.out.print("Password: ");
            pass = this.keyboard.nextLine();
        
        return pass;
    }
    
    private void clearKeyboardInputBuffer(){
        if(this.keyboard.hasNextLine()){
            this.keyboard.hasNextLine();
        }
    }
    
    private void pauseConsole(){
        this.clearKeyboardInputBuffer();
        System.out.println("");
        System.out.println(ENTER_TO_CONTINUE);
        this.keyboard.nextLine();
    }
    
    private void stop(){
        this.running = false;
    }
        
    
    
    
}
