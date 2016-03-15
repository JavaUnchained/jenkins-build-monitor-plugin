package core_jenkins.tasks;

import core_jenkins.user_interface.JenkinsHomePage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ScheduleABuild implements Task {
    public static Task of(String project) {
        return instrumented(ScheduleABuild.class, project);
    }

    @Override
    @Step("{0} schedules a build of the '#project' project  ")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(JenkinsHomePage.Schedule_A_Build.of(project))
        );
    }

    public ScheduleABuild(String project) {
        this.project = project;
    }

    private final String project;
}