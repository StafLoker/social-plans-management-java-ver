package org.stafloker.console.commands.planCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.services.PlanService;
import org.stafloker.services.Session;

@Controller
public class EnrollSubscriber implements Command {
    private static final String VALUE = "plan-join";
    private static final String PARAMETER_HELP = "<id_plan>";
    private static final String COMMENT_HELP = "Subscriber an existing plan";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public EnrollSubscriber(PlanService planService, View view, Session session) {
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        if (values.length != 1) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        Plan plan = this.planService.enrollSubscriber(Long.parseLong(values[0]));
        this.view.showPlan(plan.getId(), plan.getName(), plan.getOwner().getName(), plan.getDate(), plan.getMeetingPlace(), plan.getCapacity(), plan.availableSpots(), plan.getActivities().stream().map(activity -> activity.getName()).toList(), plan.getSubscribers().stream().map(subscriber -> subscriber.getName()).toList());
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
