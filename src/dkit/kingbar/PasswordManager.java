/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author kingBar
 */
public class PasswordManager
{
    private ArrayList<PasswordEntity> passwordEntities;
    private String key;
    
    
    public PasswordManager()
    {
        Authenticate authenticate = new Authenticate();
        
        if( authenticate.getResult() )
        {
            this.passwordEntities = authenticate.getPasswordEntities();
            this.key = authenticate.getAuthenticationKey();
            this.runApplication();       
        }
        else
        {
            System.out.println("There was An Issue Somewhere.");
        }      
        
    }
    
    
    public void showMenu()
    {
        System.out.println("Option 0: Logout");
        System.out.println("Option 1: View All Password Entity");
        System.out.println("Option 2: Find Password Entity By Title");
        System.out.println("Option 3: Add New Password Entity");
        System.out.println("Option 4: Update Existing Password Entity By ID");
        System.out.println("Option 5: Delete A Password Entity By ID");
        System.out.println("Option 6: Change Master Password\n"); 
    }
    
    public void runApplication()
    {
        this.showMenu();
        String option = Keyboard.getStringInput("Please Choose An Option: ");      
        
        while (!option.equals("0"))
        {
            if (option.equals("1"))
            {
                this.viewAllPasswordEntities();
                System.out.println("\n");
            }
            else if (option.equals("2"))
            {
                this.findPasswordEntitiesByTitle();
                System.out.println("\n");
            }
            else if (option.equals("3"))
            {
                this.addNewPassworddEntity();
                System.out.println("\n");
            }
            else if (option.equals("4"))
            {
                this.updatePassworddEntityByID();
                System.out.println("\n");
            }
            else if (option.equals("5"))
            {
                this.deletePassworddEntityById();
                System.out.println("\n");
            }
            else if (option.equals("6"))
            {
                this.changeMasterPassword();
                System.out.println("\n");
            }
            else
            {
                System.out.println("Invalid Option Entered.\n");
            }
            showMenu();
            option = Keyboard.getStringInput("Choose Option: ");
        }
    }
    
    
    public void viewAllPasswordEntities()
    {
        if( this.passwordEntities.size() > 0 )
        {
            int showMaxEntries = Keyboard.getIntInputWithMin("Maximum Entries To Show: ", 1);
            
            //SORT THE passwordEntities BY TITLE AS DEFINED IN THE PasswordEntities CLASS.
            Collections.sort(this.passwordEntities);
            
            System.out.println("List Of Entities Sorted By Title:");
            for( int i = 0; i < this.passwordEntities.size(); i++ )
            {
                System.out.println("("+(i+1)+") "+this.passwordEntities.get(i).getLimitedData());
                
                if( i == showMaxEntries-1 )
                {
                    break;
                }
            }
            
            boolean validateInput = true;
            while( validateInput )
            {
                String option = Keyboard.getStringInput("Views Full Details Of Entities? [yes or no]: ");
                if( option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("y") )
                {
                    this.viewAllPasswordEntitiesWithAllDataShown(showMaxEntries);
                    validateInput = false;
                }
                else if( option.equalsIgnoreCase("no") || option.equalsIgnoreCase("n"))
                {
                    validateInput = false;
                }
                else
                {
                    System.out.println("Invalid Input.\n");
                }
                        
            }
        }
        else
        {
            System.out.println("Password Entity Is Empty\n");
        }
    }
    
    
    private void viewAllPasswordEntitiesWithAllDataShown(int showMaxEntries)
    {
        //SORT THE passwordEntities BY TITLE AS DEFINED IN THE PasswordEntities CLASS.
        Collections.sort(this.passwordEntities);
        
        System.out.println("List Of Entities With Full Details Sorted By Title");
        for( int i = 0; i < this.passwordEntities.size(); i++ )
        {
            System.out.println("("+(i+1)+") "+this.passwordEntities.get(i).toString());
            if( i == showMaxEntries-1 )
            {
                break;
            }
        }
    }
    
    
    public void findPasswordEntitiesByTitle()
    {
        String title = Keyboard.getStringInput("Enter Title To Find: ");
        int count = 1;
        String searchResult = "Password Entities: \n";      
        
        for( PasswordEntity passwordEntity : this.passwordEntities )
        {
            if( passwordEntity.getTitle().equalsIgnoreCase(title) )
            {
                searchResult +="("+count+") "+passwordEntity.getLimitedData()+"\n";
                count++;
            }         
        }
        if( count > 1 )
        {
            System.out.println(searchResult);
            
            boolean validateInput = true;
            while( validateInput )
            {
                String option = Keyboard.getStringInput("Views Full Details Of The Entities? [yes or no]: ");
                if( option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("y"))
                {
                    this.findPasswordEntitiesByTitleShowFullData(title);
                    validateInput = false;
                }
                else if( option.equalsIgnoreCase("no") || option.equalsIgnoreCase("n") )
                {
                    validateInput = false;
                }
                else
                {
                    System.out.println("Invalid Input.\n");
                }
                        
            }
        }
        else
        {
            System.out.println("No Result Found.\n");
        }
        
    }
    
    
    private void findPasswordEntitiesByTitleShowFullData(String title)
    {
        int count = 1;
        System.out.println("Full Details.");
        for( PasswordEntity passwordEntity : this.passwordEntities )
        {
            if( passwordEntity.getTitle().equalsIgnoreCase(title) )
            {
                System.out.println("("+count+") "+passwordEntity.toString());
            }
            count++;
        }
    }
    
    
    public void addNewPassworddEntity()
    { 
        String title = Keyboard.getStringInput("Enter Title: ");
        String website = Keyboard.getStringInput("Enter the Website URL: ");
        String websiteError = Validate.entityURL(website);
        while( websiteError.length() != 0 )
        {
            System.out.println("Invalid Website.");
            website = Keyboard.getStringInput("Enter the Website URL: ");
            websiteError = Validate.entityURL(website);
        }
        
        String password = this.validateEntityPassword();
        
        //SORT THE PassWordEntities BY id AND INCREMENT BY 1 FROMTHE LAST ID IN THE ENTITY LIST  
        Collections.sort(this.passwordEntities, new PasswordEntityIDComparator() );
        
        int id = 1;
        if( this.passwordEntities.size() > 0 )
        {
            id = this.passwordEntities.get(this.passwordEntities.size()-1 ).getId()+1;
        }
        
        
        LocalDateTime date = LocalDateTime.now();
        
        PasswordEntity passwordEntity = new PasswordEntity(id, title, website, password, date );
        
        this.passwordEntities.add(passwordEntity);
        this.updatePasswordStoreFile();
        System.out.println("ADDED SUCCESSFULLY\n");
    }
    
    public void updatePassworddEntityByID()
    {
        String id = Keyboard.getStringInput("Password Entity ID To Update: ");
        boolean idFound = false;
        for( PasswordEntity passwordEntity : this.passwordEntities )
        {
            String idConvertedToString = ""+passwordEntity.getId();
            if( idConvertedToString.equals(id) )
            {
                idFound = true;
                String title = Keyboard.getStringInput("Update Title: ");               
                String website = Keyboard.getStringInput("Update the Website URL: ");
                
                //VALIDATE THE WEBSITE URL
                String websiteError = Validate.entityURL(website);
                while( websiteError.length() != 0 )
                {
                    System.out.println("Invalid Website.");
                    website = Keyboard.getStringInput("Enter the Website URL: ");
                    websiteError = Validate.entityURL(website);
                }
                
                //String password = Keyboard.getStringInput("Update Password: ");
                String password = this.validateEntityPassword();
                
                passwordEntity.setTitle(title);
                passwordEntity.setWebsite(website);
                passwordEntity.setPassword(password);
                passwordEntity.setLastUpdated(LocalDateTime.now());
                this.updatePasswordStoreFile();
                System.out.println("Updated Successfuly.\n");
                break;
            }
        }
        if( !idFound )
        {
            System.out.println("That ID Was Not Found In The System.\n");
        }
    }
    
    public void deletePassworddEntityById()
    {
        String id = Keyboard.getStringInput("Enter Password Entity Id To Delete: ");
        for( PasswordEntity passwordEntity : this.passwordEntities )
        {
            String idConvertedToString = ""+passwordEntity.getId();
            if( idConvertedToString.equals(id)  )
            {
                String confirm = Keyboard.getStringInput("Are You Sure? Yes or No: ");
                while( !confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("no") && !confirm.equalsIgnoreCase("n") ) 
                {
                    System.out.println("Invalid Imput\n");
                    confirm = Keyboard.getStringInput("Are You Sure? [Yes or No]: ");
                }
                
                if( confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y"))
                {
                    this.passwordEntities.remove(passwordEntity);
                    System.out.println("Password Entity ID "+id+" Was Removed Successfully.\n");
                    this.updatePasswordStoreFile();
                    break;
                }
                else
                {
                    System.out.println("Operation Cancelled.\n");
                }
                
            }
        }
    }
    
    public void changeMasterPassword()
    {
        String password = Keyboard.getStringInput("Please Enter Current Password: ");
        String error = Validate.userApplicationPassword(password);
        if( error.length() == 0 )
        {
            String[] authenticationData = File.readAuthenticationData();
            Password passwordObject = new Password(password, authenticationData[0]);
            
            if( passwordObject.matchesHash(authenticationData[2]) )
            {
                String newPassword = Keyboard.getStringInput("Enter New Password: ");
                error = Validate.userApplicationPassword(newPassword);
                while( error.length() != 0 )
                {
                    System.out.println(error);
                    newPassword = Keyboard.getStringInput("Enter New Password: ");
                    error = Validate.userApplicationPassword(newPassword);
                }
                
                //GENERATE NEW SALT AND NEW HASH
                String saltOne = Password.generateRandomSalt();
                String saltTwo = Password.generateRandomSalt();
                Password accountPassword = new Password(newPassword, saltOne);
                String hash = accountPassword.generateHash();
                
                //GENERATE THE NEW SECURITY KEY FOR DERIVING THE FINAL AES ECRYPTION AND DECRYPTION
                this.key = new Password(newPassword, saltTwo).generateHash();
                
                File.writeDataToFile("user.txt", saltOne+" "+saltTwo+" "+hash);
                
                //UPDATE THE passwordStore WITH THE NEW ENCRYPTION
                this.updatePasswordStoreFile();
                System.out.println("Master Password Changed Successfully.\n");
                
            }
            else
            {
                System.out.println("Invalid Password Entered.\n");
            }
        }
        else
        {
            System.out.println("Invalid Password Entered.\n");
        }
    }
    
    
    public String serializePasswordEntityListToString()
    {
        String result = "";
        for ( PasswordEntity passwordEntity : this.passwordEntities )
        {
            result += passwordEntity.getSerialize()+"\n";
        }
        return result;
    }
    
    
    public void updatePasswordStoreFile()
    {
        String[] authenticationData = File.readAuthenticationData();
        //Password passwordObject = new Password(this.key, authenticationData[1]);
        
        String plainText = this.serializePasswordEntityListToString();
        //String cipherText = Cipher.encryptString(plainText, passwordObject.generateHash());
        String cipherText = Cipher.encryptString(plainText, this.key);
        File.writeDataToFile("passwordStore.txt", cipherText);
    }
    
    
    public boolean entityPasswordUsedBefore(String password)
    {
        boolean result = false;
        for( PasswordEntity passwordEntity : this.passwordEntities )
        {
            if( passwordEntity.getPassword().equals(password) )
            {
                result = true;
                break;
            }
        }
        return result;
    }
    
    
    public String validateEntityPassword( )
    {
        String password = Keyboard.getStringInput("Enter Password: ");
        boolean passwordHasBeenUsed = this.entityPasswordUsedBefore(password);
        String passwordError = Validate.userApplicationPassword(password);      
        boolean overRide = !passwordHasBeenUsed && passwordError.length()== 0 ;
        
        while( !overRide )
        {
            if (passwordHasBeenUsed)
            {
                System.out.println("This PassWord Has Been Used Before With An Entity.");
            }
            if( passwordError.length() > 0 )
            {
                System.out.println("The Password Didn't Meet Our Recommendation.\n"
                        + "We recommend Minimum 15 Digits Including Atleast - "
                        + "One Capital Letter, One Small Letter, One Number, And One Symbol.\n");
            }
            
            String answer = Keyboard.getStringInput("Do You Want To Still Use The Password? [yes or no]: ");
            
            
            while( !answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("y")  && !answer.equalsIgnoreCase("no") && !answer.equalsIgnoreCase("n") )
            {
                answer = Keyboard.getStringInput("Invalid Input. Answer [yes or no]: ");            
            }
            
            if( answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n") )
            {
                password = Keyboard.getStringInput("Enter Password: ");
                passwordHasBeenUsed = this.entityPasswordUsedBefore(password);
                passwordError = Validate.userApplicationPassword(password);      
                overRide = !passwordHasBeenUsed && passwordError.length()==0 ;
            }
            else if( answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y"))
            {
                overRide = true;
            }        
            
        }
        return password;
    }
}
