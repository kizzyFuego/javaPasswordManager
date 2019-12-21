/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

import java.time.LocalDateTime;

/**
 *
 * @author kingbar
 */
public class PasswordEntity implements Comparable<PasswordEntity>
{
    private int id;
    private String title;
    private String website;
    private String password;
    private LocalDateTime lastUpdated;

    public PasswordEntity(int id, String title, String website, String password, LocalDateTime lastUpdated)
    {
        this.setId(id);
        this.setTitle(title);
        this.setWebsite(website);
        this.setPassword(password);
        this.setLastUpdated(lastUpdated);
    }

    

    public int getId()
    {
        return id;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public String getWebsite()
    {
        return this.website;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public LocalDateTime getLastUpdated()
    {
        return lastUpdated;
    }
    
    

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        
        //this.title = title.trim().replaceAll("[ ]+", " ").replaceAll("[,]", "  ");|
        this.title = title.trim().replaceAll("|", "").replaceAll("\n", "");
        
    }   

    public void setWebsite(String website)
    {
        //this.website = website.trim().replaceAll("[ ]+", " ").replaceAll("[,]", "  ");
        this.website = website.trim().replaceAll("|", "").replaceAll("\n", "");
    }   

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setLastUpdated(LocalDateTime lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }
    
    public String getLimitedData()
    {
        return this.getTitle()+" ("+this.getWebsite()+")";        
    }
    
    public String getSerialize()
    {
        return this.id
                +"|"+this.title
                +"|"+this.website
                +"|"+this.password
                +"|"+this.lastUpdated;
    }
    
    @Override
    public String toString()
    {
        return "{Id: "+this.getId()
                +", Title: "+this.getTitle()
                +", Website: "+this.getWebsite()
                +", Password: "+this.getPassword()
                +", LastUpdated: "+this.getLastUpdated()+"}";
    }
    
    @Override
    public int compareTo(PasswordEntity passwordEntity)
    {
        return this.getTitle().compareTo(passwordEntity.getTitle());
    } 
}
