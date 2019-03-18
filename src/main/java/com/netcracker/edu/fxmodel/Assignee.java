package com.netcracker.edu.fxmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


public class Assignee {

    private static final Logger log = LoggerFactory.getLogger(Assignee.class);

    private  StringProperty name;


    /**
     * Конструктор по умолчанию.
     */
    public Assignee() {
        this(null);
    }

    public Assignee(String name) {
        this.name = new SimpleStringProperty(name);
    }
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }


    @Override
    public String toString() {
        return name.getValue();
    }
}
