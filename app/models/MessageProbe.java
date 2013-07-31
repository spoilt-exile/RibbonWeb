/**
 * This file is part of RibbonWeb application (check README).
 * Copyright (C) 2012-2013 Stanislav Nepochatov
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.*;
import com.avaje.ebean.validation.*;
import play.data.format.*;

/**
 * Message post probe model;
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
@Entity
public class MessageProbe extends Model {
    
    @Id
    public String id;
    
    /**
     * Header of message.
     */
    @NotNull
    @Length(max=450)
    public String header;
    
    /**
     * Date of message creating.
     */
    @NotNull
    @Formats.DateTime(pattern="HH:mm:ss dd.MM.yyyy")
    @Past
    public java.util.Date date = new java.util.Date();
    
    /**
     * Pseudo directory to post.
     */
    @NotNull
    public String pseudo_dir = "Тест";
    
    /**
     * Tags of message.
     */
    public String tags;
    
    /**
     * Content of message.
     */
    @NotNull
    @Length(max=4000000)
    public String content;
    
    /**
     * Author of this message.
     */
    @NotNull
    public String author;
    
    /**
     * Status of message processing stage.
     */
    public enum STATUS {
        
        /**
         * Message is new in the system: post required.
         */
        NEW,
        
        /**
         * Message has been posted to the system: do nothing.
         */
        POSTED,
        
        /**
         * Not posted, get error: notify user.
         */
        WITH_ERROR,
        
        /**
         * Marked to delete from database: will delete soon.
         */
        DELETED
    }
    
    /**
     * Current status of this probe.
     */
    @NotNull
    public STATUS curr_status = STATUS.NEW;
    
    /**
     * Posting error.
     */
    public String curr_error = null;
    
    /**
     * Get message probe CSV representation for post to the system.
     * @return formatted to CSV command;
     */
    public String getCsvToPost() {
        return "RIBBON_POST_MESSAGE:-1,[СИСТЕМА.Тест],UKN,{" + this.header + "},[" + tags.replaceAll(" ", "") + "],{}\n" + content + "\nEND:";
    }
}
