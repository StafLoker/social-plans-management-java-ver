package org.stafloker.console.commands.planCommands.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.services.PlanService;

@Controller
public class DeletePlan implements Command {
    private static final String VALUE = "plan-delete";
    private static final String PARAMETER_HELP = "<plan_id>";
    private static final String COMMENT_HELP = "Deletes the plan";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public DeletePlan(PlanService planService, View view, Session session) {
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
        this.planService.delete(Integer.parseInt(values[0]), user);
        this.view.showResult("Plan with id=" + values[0] + " deleted successfully");
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
