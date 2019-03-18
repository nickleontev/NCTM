package com.netcracker.edu.util.xmladapters;

import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.fxmodel.TaskStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class TaskStatusAdapter extends XmlAdapter<String, TaskStatus> {
    public TaskStatus unmarshal(String id) throws Exception {
        return TaskStatus.getInstanceById(id);
    }

    public String marshal(TaskStatus v) throws Exception {
        return v.getId();
    }
}
