package com.netcracker.edu.util;

import com.netcracker.edu.fxmodel.Project;
import javafx.collections.ArrayChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class NotificationController {
    private TrayIcon trayIcon;
    private ArrayList<LocalDate> dates;
    SystemTray tray;

    public NotificationController(){
    }

    public void addListener(ObservableList list){
        list.addListener((ListChangeListener) c -> {ObservableList observableList = c.getList();
            ArrayList<LocalDate> dates = new ArrayList();
            for (Object o: observableList) {
                //dates.add(
                dates.add(((Project) o).getDeadline());
            }
        });
    }
    public void displayTray() throws AWTException{
        //Obtain only one instance of the SystemTray object
        tray = SystemTray.getSystemTray();
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("images/icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);


    }
    public void displayMessage(String caption, String text){
        //trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
        trayIcon.displayMessage("JOPA", "JOADASODJASODPAJSDO", TrayIcon.MessageType.INFO);
    }
    public void end(){
        tray.remove(trayIcon);
    }
    public void displayMessageService(String caption, String text){

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        //for (LocalDate d : dates) {
            //if (d.equals(LocalDate.now())) {

                service.schedule(() -> {
                    trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
                }, 15000, TimeUnit.MILLISECONDS);
           // }
        //}


    }
}