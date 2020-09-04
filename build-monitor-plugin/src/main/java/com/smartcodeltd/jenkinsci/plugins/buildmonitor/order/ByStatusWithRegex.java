package com.smartcodeltd.jenkinsci.plugins.buildmonitor.order;

import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;

import java.io.Serializable;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.regex.Pattern;

import static com.smartcodeltd.jenkinsci.plugins.buildmonitor.RegexMatcher.selectSpecificBuild;

public class ByStatusWithRegex implements Comparator<JobByRegexForStatusTemplate>, Serializable {

    @Override
    public int compare(JobByRegexForStatusTemplate a, JobByRegexForStatusTemplate b) {
        return bothProjectsHaveBuildHistory(a, b)
                ? compareRecentlyCompletedBuilds(a, b)
                : compareProjects(a, b);
    }

    private boolean bothProjectsHaveBuildHistory(JobByRegexForStatusTemplate a, JobByRegexForStatusTemplate b) {
        return a.getProjects().getLastCompletedBuild() != null && b.getProjects().getLastCompletedBuild() != null;
    }

    private int compareProjects(JobByRegexForStatusTemplate a, JobByRegexForStatusTemplate b) {
        Run<?, ?> recentBuildOfA = a.getProjects().getLastCompletedBuild();
        Run<?, ?> recentBuildOfB = b.getProjects().getLastCompletedBuild();

        if (recentBuildOfA == null && recentBuildOfB != null) {
            return -1;
        } else if (recentBuildOfA != null && recentBuildOfB == null) {
            return 1;
        } else {
            return 0;
        }
    }

    private int compareRecentlyCompletedBuilds(JobByRegexForStatusTemplate a, JobByRegexForStatusTemplate b) {
        Result lastResultOfA = iterateByJobs(a);
        Result lastResultOfB = iterateByJobs(b);

        if (lastResultOfA == null && lastResultOfB == null) {
            return 0;
        } else if (lastResultOfA == null) {
            return -1;
        } else if (lastResultOfB == null) {
            return 1;
        } else if (lastResultOfA.isWorseThan(lastResultOfB)) {
            return -1;
        } else if (lastResultOfA.isBetterThan(lastResultOfB)) {
            return 1;
        } else {
            return 0;
        }
    }

    private Result iterateByJobs(JobByRegexForStatusTemplate job){
        Job<?,?> j =  job.getProjects();
        Run run = selectSpecificBuild(j, job.regex);
        return run == null ? j.getLastCompletedBuild().getResult(): run.getResult();
    }
}
