package org.stafloker.console.commands.activityCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.services.ActivityService;
import org.stafloker.services.Session;

@Controller
public class CreateActivity implements Command {
    private static final String VALUE = "create-activity";
    private static final String PARAMETER_HELP = "<type: Generic, Theatre, Cinema>, <name>;<description>;<duration: minutes>;<cost>;<<capacity>>";
    private static final String COMMENT_HELP = "Create a new activity";

    private final Session session;
    private final ActivityService activityService;
    private final View view;

    @Autowired
    public CreateActivity(ActivityService activityService, View view, Session session) {
        this.activityService = activityService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        if ((values.length < 5 || values.length > 6) || !(values[0].equalsIgnoreCase("Generic") || values[0].equalsIgnoreCase("Theatre") || values[0].equalsIgnoreCase("Cinema"))) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        Activity createdActivity = this.activityService.create(values[0], values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]), Integer.parseInt(values[5]));
        this.view.showActivity(createdActivity.getId(), createdActivity.getName(), createdActivity.getDescription(), createdActivity.getClass().getSimpleName(), createdActivity.getDuration(), createdActivity.getCapacity(), createdActivity.getPrice());
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
}
