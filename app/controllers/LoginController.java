package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.defaultpages.error;

public class LoginController extends Controller {
	
	private static String errorLogin = null;
  
    public static Result index() {
    	if (!MiniGate.isGateInited) {
    		return ok(ribbon_error.render("Неможливо з'єднатись з сервером!"));
    	} else {
    		return ok(login.render("Вхід до системи...", LoginController.errorLogin));
    	}
    }
    
    public static Result login() {
    	// TODO implement login method with cookies
    	return redirect(routes.LoginController.index());
    }
  
}
