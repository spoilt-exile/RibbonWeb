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

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.defaultpages.error;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
