package com.netcracker.edu.util.xmladapters;


import com.netcracker.edu.fxmodel.Project;
import com.netcracker.edu.fxmodel.Task;
import com.netcracker.edu.fxmodel.TaskStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TaskAdapter extends XmlAdapter<Task, Task> {
    @Override
    public Task unmarshal(Task v) throws Exception {
     //   List<Project> list = v.getProjectList();

        for (int i=0; i<v.getNotificationList().size();i++) {
            v.getNotificationList().get(i).setParentTask(v);
        }
        return v;
    }

    @Override
    public Task marshal(Task v) throws Exception {
        return v;
    }
}
