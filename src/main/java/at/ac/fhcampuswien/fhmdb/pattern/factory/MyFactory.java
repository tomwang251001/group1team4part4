package at.ac.fhcampuswien.fhmdb.pattern.factory;

import at.ac.fhcampuswien.fhmdb.HomeController;

import javax.security.auth.callback.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    // TODO: create MyCtrl as singleton instance
    private static HomeController instanceOfHomecontroller;
    public HomeController getHomeController(){
        if (instanceOfHomecontroller == null) {
            instanceOfHomecontroller = new HomeController();
        }
        return instanceOfHomecontroller;
    }
    @Override
    public Object call(Class<?> aClass) {
    // TODO: check if MyCtrl is already instantiated
        try{
            return (getHomeController()); aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
