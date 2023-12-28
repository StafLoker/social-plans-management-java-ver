package org.stafloker.console.commands.planCommands;

import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.services.PlanService;

public class GetPlanDuration implements Command {
    private static final String VALUE = "plan-duration";
    private static final String PARAMETER_HELP = "<id_plan>";
    private static final String COMMENT_HELP = "Get the duration of the plan";

    private final Session session;
    private final PlanService planService;
    private final View view;

    public GetPlanDuration(PlanService planService, View view, Session session) {
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        User user = this.session.getSecuredUser();
        if (values.length != 1) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        int duration = this.planService.duration(Integer.parseInt(values[0]), user);
        this.view.showResult(duration / 60 + " hour/s " + duration % 60 + " minute/s");
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