package com.smartcodeltd.jenkinsci.plugins.buildmonitor.order;

import hudson.model.Job;

public class JobByRegexForStatusTemplate {
    Job<?, ?> projects;
    final String regex;

    public JobByRegexForStatusTemplate(Job<?, ?> projects, String regex) {
        this.projects = projects;
        this.regex = regex;
    }

    public Job<?, ?> getProjects() {
        return projects;
    }

    public void setProjects(Job<?, ?> projects) {
        this.projects = projects;
    }

    public String getRegex() {
        return regex;
    }
}
