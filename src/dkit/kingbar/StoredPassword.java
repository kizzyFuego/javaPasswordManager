package dkit.kingbar;

/**
 *
 * @author Kingbar
 * 
 */
public class StoredPassword {
    
    private int id;
    private String title;
    private String website;
    private String password;
    private String lastUpdated;
    
    public StoredPassword(){
        setID(0);
        setTitle(null);
        setWebsite(null);
        setPassword(null);
        setLastUpdated(null);
        
    }
    
    public StoredPassword(int id, String title, String website, String password, String lastUpdated){
        setID(id);
        setTitle(title);
        setWebsite(website);
        setPassword(password);
        setLastUpdated(lastUpdated);
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getWebsite(){
        return this.website;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getLastUpdated(){
        return this.lastUpdated;
    }
    
    public void setID(int newID){
        this.id = newID;
    }
    
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    
    public void setWebsite(String newWebsite){
        this.website = newWebsite;
    }
    
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    
    public void setLastUpdated(String newLastUpdated){
        this.lastUpdated = newLastUpdated;
    }
    
    
    
    
    
    
}
