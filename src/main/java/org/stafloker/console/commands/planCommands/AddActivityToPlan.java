package org.stafloker.console.commands.planCommands;

import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.services.PlanService;

public class AddActivityToPlan implements Command {
    private static final String VALUE = "plan-add-activity";
    private static final String PARAMETER_HELP = "<plan_id>;<activity_id>";
    private static final String COMMENT_HELP = "Add activity to the plan";

    private final Session session;
    private final PlanService planService;
    private final View view;

    public AddActivityToPlan(PlanService planService, View view, Session session) {
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        User user = this.session.getSecuredUser();
        if (values.length != 2) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        this.view.showPlan(this.planService.addActivity(Integer.parseInt(values[0]), Integer.parseInt(values[1]), user));
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
