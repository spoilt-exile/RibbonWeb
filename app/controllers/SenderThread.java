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

package controllers;

import java.util.List;
import play.db.ebean.Model;

/**
 * Thread wich post messages to the server and mark probes.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class SenderThread extends Thread {
    
    @Override
    public void run() {
        while (MiniGate.isGateReady) {
            postMessages();
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException ex) {
                //Do nothing just postMessages()
            }
        }
    }
    
    /**
     * Check database for new messages post and mark them.
     */
    private void postMessages() {
        List<models.MessageProbe> probes = new Model.Finder(String.class, models.MessageProbe.class).where().ne("curr_status", 1).findList();
        for (models.MessageProbe currProbe : probes) {
            String contextErr = MiniGate.gate.sendCommandWithCheck("RIBBON_NCTL_ACCESS_CONTEXT:{" + currProbe.author + "}");
            if (contextErr != null) {
                currProbe.curr_status = models.MessageProbe.STATUS.WITH_ERROR;
                currProbe.curr_error = contextErr;
                currProbe.update();
            } else {
                String postErr = MiniGate.gate.sendCommandWithCheck(currProbe.getCsvToPost());
                if (postErr != null) {
                    currProbe.curr_status = models.MessageProbe.STATUS.WITH_ERROR;
                    currProbe.curr_error = postErr;
                } else {
                    currProbe.curr_status = models.MessageProbe.STATUS.POSTED;
                    currProbe.curr_error = null;
                }
                currProbe.update();
            }
        }
    }
    
}
