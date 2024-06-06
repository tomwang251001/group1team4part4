package at.ac.fhcampuswien.fhmdb.pattern.factory;

import at.ac.fhcampuswien.fhmdb.HomeController;

import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    private static HomeController instanceOfHomecontroller;
    public HomeController getHomeController(){
        if (instanceOfHomecontroller == null) {
            instanceOfHomecontroller = new HomeController();
        }
        return instanceOfHomecontroller;
    }
    @Override
    public Object call(Class<?> aClass) {
        try{
            return  getHomeController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
