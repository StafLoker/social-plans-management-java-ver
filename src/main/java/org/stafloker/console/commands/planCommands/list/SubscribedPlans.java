package org.stafloker.console.commands.planCommands.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.commands.planCommands.PlanCommand;
import org.stafloker.data.models.User;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.services.PlanService;
import org.stafloker.services.Session;

@Controller
public class SubscribedPlans extends PlanCommand implements Command {
    private static final String VALUE = "subscribed-plans";
    private static final String PARAMETER_HELP = "";
    private static final String COMMENT_HELP = "View plans subscribed by the user";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public SubscribedPlans(PlanService planService, View view, Session session) {
        super();
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        this.sortByDate(this.planService.subscribedPlans())
                .forEach(plan -> this.view.showPlanForList(plan.getId(), plan.getName(), plan.getOwner().getName(), plan.getDate(), plan.getMeetingPlace(), plan.getCapacity(), plan.availableSpots(), plan.getActivities().stream().map(Activity::getName).toList(), plan.getSubscribers().stream().map(User::getName).toList()));
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
