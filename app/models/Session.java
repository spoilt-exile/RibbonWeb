/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.*;

/**
 * Session model class.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
@Entity
public class Session extends Model {
    
    @Id
    public String id;
    
    //@Required
    public String username;
    
    //@Required
    public String password;
    
}
