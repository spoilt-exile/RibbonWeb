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
 * Server configuration model;
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
@Entity
public class ServerConfig {
    
    @Id
    public String id;
    
    /**
     * Name of this configuration.
     */
    public String name;
    
    /**
     * Address of the Ribbon message server.
     */
    public String address;
    
    /**
     * Port of the Ribbon message server.
     */
    public Integer port;
    
    /**
     * User of the Ribbon system which deploy gate connection (see Ribbon specifications).
     */
    public String ruser;
    
    /**
     * Row password of the Ribbon user.
     */
    public transient String rpass;
    
    /**
     * Hashed password of the Ribbon user.
     */
    public String hpass;
    
    /**
     * Status enumeration.
     */
    public enum STATUS {
        
        /**
         * Last connection with this configuration was successful.
         */
        VALID,
        
        /**
         * Last connection with this configuration was unsuccessful.
         */
        INVALID,
        
        /**
         * Currently using.
         */
        CURRENT
    }
    
    /**
     * Current status of configuration (INVALID by default).
     */
    public STATUS curr_status = STATUS.INVALID;
}
