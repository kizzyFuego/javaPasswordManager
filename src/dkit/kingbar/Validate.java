/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

import java.util.regex.Pattern;

/**
 *
 * @author kingbar
 */
public class Validate
{
    public static String userApplicationPassword(String password)
    {
        String error = ""; 
        if(password.length() < 15)
        {
            error += "Password Length Too Short\n";
        }
        if( !Pattern.matches(".*[A-Z].*", password) ) //Regex Idea was gotten from https://stackoverflow.com/questions/24086968/tell-if-string-contains-a-z-chars/24087063
        {
            error += "Password Must Include A Capital Letter\n";
        }
        if( !Pattern.matches(".*[a-z].*", password) ) 
        {
            error += "Password Must Include A Small Leterr\n";
        }
        if( !Pattern.matches(".*[0-9].*", password) ) 
        {
            error += "Password Must Include A Number\n";
        }
        if( !Pattern.matches(".*[^A-Za-z0-9].*", password) ) 
        {
            error += "Password Must Include A Foreign Symbol (e.g @?.*&%)\n";
        }
        
        return error;
    }
    
    
    public static String entityURL( String url )
    {
        String error = "";
        if( !Pattern.matches(".+[\\.].+", url) ) 
        {
            error += "Invalid Url";
        }
        return error;
    }
}
