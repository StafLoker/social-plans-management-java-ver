package org.stafloker.console.commands.planCommands.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.services.PlanService;
import org.stafloker.services.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CreatePlan implements Command {
    private static final String VALUE = "create-plan";
    private static final String PARAMETER_HELP = "<name>;<date: dd-MM-yyyy HH.mm>;<meetingPlace>;<<capacity>>";
    private static final String COMMENT_HELP = "Create a new plan";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public CreatePlan(PlanService planService, View view, Session session) {
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        if (values.length < 3 || values.length > 4) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        Plan plan;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        if (values.length == 3) {
            plan = this.planService.create(values[0], LocalDateTime.parse(values[1], formatter), values[2], null);
        } else {
            plan = this.planService.create(values[0], LocalDateTime.parse(values[1], formatter), values[2], Integer.parseInt(values[3]));
        }
        this.view.showPlan(plan.getId(), plan.getName(), plan.getOwner().getName(), plan.getDate(), plan.getMeetingPlace(), plan.getCapacity(), plan.availableSpots(), plan.getActivities().stream().map(Activity::getName).toList(), plan.getSubscribers().stream().map(User::getName).toList());
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
