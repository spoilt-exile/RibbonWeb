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
 * Set of pseudo directories which are available for user.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
@Entity
public class PseudoDirectorySet extends Model {
    
    @Id
    public String id;
    
    /**
     * Username for binding.
     */
    @NotNull
    public String name;
    
    /**
     * Pseudo directories in multiline CSV form.
     */
    @NotNull
    @Length(max=20000)
    public String dirs = "{Тест},{Тестовий напрямок},[СИСТЕМА.Тест]";
    
    /**
     * Get dir names;
     * @return string array with names;
     */
    public String[] getDirs() {
        String[] parsed = dirs.split("\n");
        String[] returned = new String[parsed.length];
        for (int index = 0; index < parsed.length; index++) {
            returned[index] = Generic.CsvFormat.complexParseLine(parsed[index], 2, 1).get(0)[0];
        }
        return returned;
    }
    
    /**
     * Get directory description;
     * @param dirName dir to serch;
     * @return descripton field of specified dir;
     */
    public String getDirDesc(String dirName) {
        String[] parsed = dirs.split("\n");
        String desc = null;
        for (int index = 0; index < parsed.length; index++) {
            java.util.ArrayList<String[]> parsedArr = Generic.CsvFormat.complexParseLine(parsed[index], 2, 1);
            String currDir = parsedArr.get(0)[0];
            if (dirName.equals(currDir)) {
                desc = parsedArr.get(0)[1];
                break;
            }
        }
        return desc;
    }
    
    /**
     * Update directories from Ribbon server.
     */
    private void updateDirs() {
        if (controllers.MiniGate.isGateReady) {
            String rawDirs = controllers.MiniGate.gate.sendCommandWithContextCollect(name, "RIBBON_GET_PSEUDO:");
            if (!rawDirs.startsWith("RIBBON_ERROR:")) {
                this.dirs = rawDirs;
            }
            this.update();
        }
    }
    
    /**
     * Get directory set.
     * @param givenUser user to search;
     * @return PseudoDirectorySet
     */
    public static PseudoDirectorySet get(String givenUser) {
        PseudoDirectorySet gettedSet = (PseudoDirectorySet) new Model.Finder(String.class, PseudoDirectorySet.class).where().eq("name", givenUser).findUnique();
        if (gettedSet == null) {
            gettedSet = new PseudoDirectorySet();
            gettedSet.name = givenUser;
            if (controllers.MiniGate.isGateReady) {
                String rawDirs = controllers.MiniGate.gate.sendCommandWithContextCollect(givenUser, "RIBBON_GET_PSEUDO:");
                if (!rawDirs.startsWith("RIBBON_ERROR:")) {
                    gettedSet.dirs = rawDirs;
                }
            }
            gettedSet.save();
        } else if (controllers.MiniGate.isGateReady) {
            gettedSet.updateDirs();
        }
        return gettedSet;
    }
    
}
