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
