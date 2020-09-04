package com.smartcodeltd.jenkinsci.plugins.buildmonitor;

import hudson.model.Job;
import hudson.model.Run;

import java.util.ListIterator;
import java.util.regex.Pattern;

public class RegexMatcher {
    public static Run selectSpecificBuild(Job<?, ?> job,String regex){
        if (regex != null && !regex.equals("")) {
            Pattern pattern = Pattern.compile(regex);
            Run run;
            for (ListIterator i = job.getBuilds().listIterator(); i.hasNext(); ) {
                run = (Run) i.next();
                if(pattern.matcher(run.getDisplayName()).matches()){
                    return run;
                }
            }
        }
        return null;
    }
}
