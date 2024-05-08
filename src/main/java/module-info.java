module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    //necessary to import @DatabaseTable in MovieEntity
    requires ormlite.jdbc;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;


    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
}