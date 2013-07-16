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
        return ok(dummy.render("Простого выпуска", "Форма выпуска на направление СИСТЕМА.ТЕСТ"));
    }
    
}
