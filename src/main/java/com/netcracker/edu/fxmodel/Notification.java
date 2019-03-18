package com.netcracker.edu.fxmodel;

import com.netcracker.edu.util.xmladapters.LocalDateAdapter;
import com.netcracker.edu.util.xmladapters.LocalTimeAdapter;
import com.netcracker.edu.util.xmladapters.NotificationModeAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.*;
import java.time.temporal.TemporalAdjusters;


import static java.time.temporal.ChronoUnit.DAYS;

public class Notification {

    private ObjectProperty<NotificationMode> mode;
    private  ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;
    private Task parentTask;
    private boolean performed = false;

    public boolean isPerformed() {
        if (performed == true){
            if (getMode()==NotificationMode.UNREPEATABLE) {
                //Удаляем это уведомление из задачи
                this.parentTask.removeNotification(this);
            } else
                if (!getNotificationDate().isEqual(LocalDate.now())) performed = false;
        }
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

    @XmlTransient
    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Notification() {
        this (null,null,null, null);
    }

//    public Notification(String mode, LocalDate date, LocalTime time) {
//        this.mode = new SimpleObjectProperty<>(NotificationMode.getInstanceById(mode));
//        this.date = new SimpleObjectProperty<>(date);
//        this.time = new SimpleObjectProperty<>(time);
//    }

    public Notification(NotificationMode mode, LocalDate date, LocalTime time, Task parentTask) {
        this.mode = new SimpleObjectProperty<>(mode);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.parentTask=parentTask;
    }

    @XmlJavaTypeAdapter(value = NotificationModeAdapter.class)
    public NotificationMode getMode() {
        return mode.get();
    }

    public ObjectProperty<NotificationMode> modeProperty() {
        return mode;
    }

    public void setMode(NotificationMode mode) {
        this.mode.set(mode);
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getDate() {
        return date.get();
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "notify")
    public LocalDate getNotificationDate() {
       // long a = DAYS.between(LocalDate.now(), date.get());
        //Сегодня
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonth().getValue();
        int day = today.getDayOfMonth();
        int lastDayOfMonth = today.withDayOfMonth(today.getMonth().length(today.isLeapYear())).getDayOfMonth();

        //Дата уведомления
        LocalDate nDay = date.get();
        int nyear = nDay.getYear();
        int nmonth = nDay.getMonth().getValue();
        int nday = nDay.getDayOfMonth();

        if (date.get()!=null && DAYS.between(LocalDate.now().atTime(LocalTime.now()), date.get().atTime(time.getValue()))<0) {
            switch (this.mode.get()){
                case DAILY: return LocalDate.now();
                case WEEKLY: {
                    return  LocalDate.now().with( TemporalAdjusters.next( date.get().getDayOfWeek()));}
                case MONTHLY: {
                    if (day<=nday){
                      return LocalDate.of(year, month, nday>lastDayOfMonth?lastDayOfMonth:nday);
                    }
                    else return  LocalDate.of(year, month+1, nday>lastDayOfMonth?lastDayOfMonth:nday);}
                case ANNUALLY: {
                    if (DAYS.between(LocalDate.now(),LocalDate.of(year, nmonth, nday))<0){
                        return LocalDate.of(year+1, nmonth, nday>lastDayOfMonth?lastDayOfMonth:nday);
                    }
                    else return  LocalDate.of(year, nmonth, nday>lastDayOfMonth?lastDayOfMonth:nday);}

                case UNREPEATABLE: return date.get();
            }
        }
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class)
    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    @Override
    public String toString() {
        return getNotificationDate().toString();
    }
}
