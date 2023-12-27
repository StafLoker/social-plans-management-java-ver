package org.stafloker.console.commands.activityCommands;

import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.msp.Activity;
import org.stafloker.data.models.msp.activityType.*;
import org.stafloker.services.ActivityService;

public class CreateActivity implements Command {
    private static final String VALUE = "activity-create";
    private static final String PARAMETER_HELP = "<name>;<description>;<duration: minutes>;<cost>;<type: Generic, Theatre, Cinema>;<<capacity>>";
    private static final String COMMENT_HELP = "Create a new activity";

    private final Session session;
    private final ActivityService activityService;
    private final View view;

    public CreateActivity(ActivityService activityService, View view, Session session) {
        this.activityService = activityService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        if (values.length < 5 || values.length > 6) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        Activity createdActivity = this.activityService.create(this.createActivityBasedOnType(values));
        this.view.showActivity(createdActivity);
    }

    @Override
    public String value() {
        return VALUE;
    }

    @Override
    public String helpParameters() {
        return PARAMETER_HELP;
    }

    @Override
    public String helpComment() {
        return COMMENT_HELP;
    }

    private Activity createActivityBasedOnType(String[] values) {
        if (values[4].equalsIgnoreCase("Theatre")) {
            if (values.length == 5) {
                return Theater.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).build();
            } else {
                return Theater.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).capacity(Integer.parseInt(values[5])).build();
            }
        } else if (values[4].equalsIgnoreCase("Cinema")) {
            if (values.length == 5) {
                return Cinema.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).build();
            } else {
                return Cinema.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).capacity(Integer.parseInt(values[5])).build();
            }
        } else if (values[4].equalsIgnoreCase("Generic")) {
            if (values.length == 5) {
                return Activity.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).build();
            } else {
                return Activity.builder().name(values[0]).description(values[1]).duration(Integer.parseInt(values[2])).price(Double.parseDouble(values[3])).capacity(Integer.parseInt(values[5])).build();
            }
        } else {
            throw new UnsupportedAttributesException(helpParameters());
        }
    }
}
