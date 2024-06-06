package at.ac.fhcampuswien.fhmdb.pattern.factory;

import at.ac.fhcampuswien.fhmdb.HomeController;

import javax.security.auth.callback.Callback;

public class Factory{//implements Callback<Class<?>, Object> {
    private static HomeController homeController;
    public HomeController getHomeController(){
        if (homeController == null) {
            homeController = new HomeController();
        }
        return homeController;
    }
    //@Override
    public Object call(Class<?> aClass) {
        try {
            return getHomeController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
