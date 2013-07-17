/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.*;

/**
 * 
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class MessageTest {
    
    @Id
    public String id;
    
    public String header;
    
    public String tags;
    
    public String content;
    
    public String getCsvToPost() {
        return "RIBBON_POST_MESSAGE:-1,[СИСТЕМА.Тест],UKN,{" + this.header + "},[" + tags.replaceAll(" ", "") + "],{}\n" + content + "\nEND:";
    }
}
