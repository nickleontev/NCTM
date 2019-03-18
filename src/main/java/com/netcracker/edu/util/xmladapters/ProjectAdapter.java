package com.netcracker.edu.util.xmladapters;


import com.netcracker.edu.fxmodel.Project;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class ProjectAdapter extends XmlAdapter<Project, Project> {
    @Override
    public Project unmarshal(Project v) throws Exception {
     //   List<Project> list = v.getProjectList();

        for (int i=0; i<v.getProjectList().size(); i++) {
            v.getProjectList().get(i).setParentProject(v);
        }

        for (int i =0; i < v.getTaskList().size(); i++){
            v.getTaskList().get(i).setParent(v);
        }
        return v;
    }

    @Override
    public Project marshal(Project v) throws Exception {
        return v;
    }
}
