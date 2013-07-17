/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.defaultpages.error;

import play.data.*;

/**
 *
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class SimpleReleaseContoller extends Controller {
    
    public static Result index() {
        if (session("connected") != null) {
            return ok(simple_release.render());
        } else {
            return redirect(routes.LoginController.index_with_error("Ви повинні зарєєструватись!"));
        }
    }
    
    public static Result post() {
        models.MessageTest newPost = Form.form(models.MessageTest.class).bindFromRequest().get();
        String postErr = MiniGate.gate.sendCommandWithCheck(newPost.getCsvToPost());
        return redirect(routes.SimpleReleaseContoller.index());
    }
    
}
