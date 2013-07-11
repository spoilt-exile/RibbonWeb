package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.defaultpages.error;

/**
 * Login controller class.
 * @author Stanislav Nepochatov <spoilt.exile@gmail.com>
 */
public class LoginController extends Controller {
    
    /**
     * Remote login error display string.
     */
    private static String errorLogin = null;

    public static Result index() {
        if (!MiniGate.isGateReady) {
            return ok(ribbon_error.render(MiniGate.gateErrorStr));
        } else {
            return ok(login.render("Вхід до системи...", LoginController.errorLogin));
        }
    }
    
    public static Result login() {
    	// TODO implement login method with cookies
    	return redirect(routes.LoginController.index());
    }
  
}
