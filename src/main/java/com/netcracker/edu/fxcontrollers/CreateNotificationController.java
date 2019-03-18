package com.netcracker.edu.fxcontrollers;

import com.netcracker.edu.util.NotificationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.ZoneId;
import java.util.Date;

public class CreateNotificationController {

    @FXML
    private Button ok_Button;

    @FXML
    private TextField textField_Time;

    @FXML
    private DatePicker deadline_DatePicker;

    private Date date;
    private NotificationController nt;

    public void ok_Clicked(ActionEvent actionEvent) {

        nt = new NotificationController();
        try {
            date = Date.from(deadline_DatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String time = textField_Time.getText();
            String[] subStr = time.split(":");

        int hours = Integer.valueOf(subStr[0]);
        int minutes = Integer.valueOf(subStr[1]);
        date.setHours(hours);
        date.setMinutes(minutes);
        nt.displayMessage("Уведомление", date.toString());
        nt.end();
        } catch (ArrayIndexOutOfBoundsException ex){
            //nt = new NotificationController();
            nt.displayMessage("Ошибка!", ex.getMessage());
            nt.end();
        }

    }
}
