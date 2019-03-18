package com.netcracker.edu.util.xmladapters;

import com.netcracker.edu.fxmodel.NotificationMode;
import com.netcracker.edu.fxmodel.TaskStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NotificationModeAdapter extends XmlAdapter<String, NotificationMode> {
    public NotificationMode unmarshal(String id) throws Exception {
        return NotificationMode.getInstanceById(id);
    }

    public String marshal(NotificationMode v) throws Exception {
        return v.getId();
    }
}
