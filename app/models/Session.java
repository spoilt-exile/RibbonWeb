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
 * Session model class.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
@Entity
public class Session extends Model {
    
    @Id
    public String id;
    
    /**
     * Name of the user.
     */
    @NotNull
    @Length(max=200)
    public String username;
    
    /**
     * Description of that user (department, phone and etc).
     */
    @NotNull
    @Length(max=400)
    public String description;
    
    /**
     * Display is user have administrative access to the system.
     */
    @NotNull
    public boolean isAdmin;

    /**
     * Raw password, excluded from saving.
     */
    public transient String password;
    
    /**
     * Enum of user statuses.
     */
    public enum STATUS {
        
        /**
         * User is online.
         */
        ONLINE,
        
        /**
         * User is offline.
         */
        OFFLINE
    }
    
    /**
     * Current status of user.
     */
    @NotNull
    public STATUS currStatus = STATUS.ONLINE;
    
    /**
     * First login of the user.
     */
    @NotNull
    @Formats.DateTime(pattern="HH:mm:ss dd.MM.yyyy")
    public java.util.Date firstLogin;
    
    /**
     * Last or current login of the user.
     */
    @NotNull
    @Formats.DateTime(pattern="HH:mm:ss dd.MM.yyyy")
    public java.util.Date lastLogin;
    
    /**
     * Fill other fields of this model.
     */
    public void init() {
        firstLogin = new java.util.Date();
        lastLogin = firstLogin;
        java.util.ArrayList<String[]> parsed = Generic.CsvFormat.complexParseLine(controllers.MiniGate.gate.sendCommandWithContextReturn(this.username, "RIBBON_NCTL_GET_USERNAME:"), 2, 1);
        this.description = parsed.get(0)[1];
        for (String group: parsed.get(1)) {
            if (group.equals("ADM")) {
                this.isAdmin = true;
                break;
            }
        }
    }
    
    /**
     * Get formatted first login date as string.
     * @param format date specific format;
     * @return formatted string;
     */
    public String getFDateWithFormat(String format) {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(format);
        String strDate = dateFormat.format(firstLogin);
        return strDate;
    }
    
    /**
     * Get formatted last login date as string.
     * @param format date specific format;
     * @return formatted string;
     */
    public String getLDateWithFormat(String format) {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(format);
        String strDate = dateFormat.format(lastLogin);
        return strDate;
    }
    
    /**
     * Update this session from base.
     */
    public void pick() {
        lastLogin = new java.util.Date();
        currStatus = STATUS.ONLINE;
    }
}
