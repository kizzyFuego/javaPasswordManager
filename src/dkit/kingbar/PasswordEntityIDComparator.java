/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kingbar;

import java.util.Comparator;

/**
 *
 * @author kingBar
 */
public class PasswordEntityIDComparator implements Comparator<PasswordEntity>
{
   
    @Override
    public int compare(PasswordEntity entityOne, PasswordEntity entityTwo)
    {
        if( entityOne.getId() < entityTwo.getId() )
        {
            return -1;
        }
        else if ( entityOne.getId() == entityTwo.getId() )
        {
            return 0;
        }
        else
        {
            return 1;
        }
    } 
}