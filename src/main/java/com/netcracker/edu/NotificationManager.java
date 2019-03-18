package com.netcracker.edu;

import com.netcracker.edu.fxmodel.*;
import com.netcracker.edu.util.RuntimeDataHolder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class NotificationManager implements Runnable {
    private Root data = RuntimeDataHolder.getHolder();

    public List<Notification> getAllNotification(Project project){
        List<Notification> notifications = new ArrayList<>();
        List<Task> tasks = project.agregateNasted();
        for (Task t: tasks) {
            notifications.addAll(t.getNotificationList());
        }
        return notifications;
        }

        private void showMessage(Notification notification) throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("images/icon32x32.png");
            TrayIcon trayIcon = new TrayIcon(image);
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            PopupMenu p = new PopupMenu();
            MenuItem item = new MenuItem("Отложить задачу");
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            p.add(new MenuItem("Ааа!"));

            Formatter f1 = new Formatter().format("NCTM: %s",notification.getParentTask().getSummary());
            Formatter f2 = new Formatter().format("Описание задачи: %s \nДата уведомления: %s \nВремя уведомления: %s", notification.getParentTask().getDescription(), notification.toString(), notification.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));

            trayIcon.setPopupMenu(p);
            trayIcon.displayMessage(f1.toString(), f2.toString(), TrayIcon.MessageType.INFO);

        }
        }

        private boolean checkNotification(Notification notification){

        if (notification!=null && !notification.isPerformed()&& notification.getNotificationDate().atTime(notification.getTime()).isBefore(LocalDate.now().atTime(LocalTime.now()))){
            notification.setPerformed(true);
            return true;
        }
        return  false;
    }

    @Override
    public void run() {
        while (true) {
            List<Notification> notifications = getAllNotification(data.getRoot());
            for (Notification n : notifications) {
                try {
                    if (checkNotification(n)) showMessage(n);
                    Thread.sleep(3000);
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
