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
import play.*;
import play.mvc.*;

import views.html.*;
import views.html.defaultpages.error;

import play.db.ebean.*;
import play.data.*;
import static play.mvc.Controller.session;

/**
 * RibbonWeb administration controller.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class AdmController extends Controller {
    
    public static Result index() {
        if (session("connected") != null && session("admin") != null && session("admin").equals("true")) {
            return ok(adm_monitor.render());
        } else {
            return redirect(routes.LoginController.index());
        }
    }
    
    public static Result configForm() {
        if ((session("connected") != null && session("admin") != null && session("admin").equals("true")) || MiniGate.currConfig == null) {
            return ok(adm_config.render(null));
        } else {
            return redirect(routes.LoginController.index());
        }
    }
    
    public static Result config() {
        models.ServerConfig newConfig = Form.form(models.ServerConfig.class).bindFromRequest().get();
        newConfig.hpass = MiniGate.getHash(newConfig.rpass);
        newConfig.curr_status = models.ServerConfig.STATUS.CURRENT;
        newConfig.save();
        Boolean flag = MiniGate.probeInit();
        if (flag) {
            return redirect(routes.LoginController.index());
        } else {
            newConfig.refresh();
            return ok(adm_config.render(newConfig));
        }
    }
    
    public static Result editForm(String id) {
        if ((session("connected") != null && session("admin") != null && session("admin").equals("true")) || MiniGate.currConfig == null) {
            models.ServerConfig currConfig = (models.ServerConfig) new Model.Finder(String.class, models.ServerConfig.class).byId(id);
            if (currConfig == null) {
                return ok(ribbon_error.render("Немає конфігурації з id=" + id));
            } else {
                return ok(adm_config.render(currConfig));
            }
        } else {
            return redirect(routes.LoginController.index());
        }
    }
    
    public static Result edit() {
        models.ServerConfig newConfig = Form.form(models.ServerConfig.class).bindFromRequest().get();
        newConfig.hpass = MiniGate.getHash(newConfig.rpass);
        newConfig.curr_status = models.ServerConfig.STATUS.CURRENT;
        newConfig.update();
        Boolean flag = MiniGate.probeInit();
        if (flag) {
            return redirect(routes.LoginController.index());
        } else {
            newConfig.refresh();
            return ok(adm_config.render(newConfig));
        }
    }
    
    public static Result list() {
        if (session("connected") != null && session("admin") != null && session("admin").equals("true")) {
            List<models.ServerConfig> confs = new Model.Finder(String.class, models.ServerConfig.class).all();
            return ok(adm_list.render(confs));
        } else {
            return redirect(routes.LoginController.index());
        }
    }
}
