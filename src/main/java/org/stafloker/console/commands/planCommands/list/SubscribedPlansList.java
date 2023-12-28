package org.stafloker.console.commands.planCommands.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.data.models.User;
import org.stafloker.console.commands.planCommands.PlanCommand;
import org.stafloker.services.PlanService;

@Component
public class SubscribedPlansList extends PlanCommand implements Command {
    private static final String VALUE = "subscribed-plans";
    private static final String PARAMETER_HELP = "";
    private static final String COMMENT_HELP = "View plans subscribed by the user";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public SubscribedPlansList(PlanService planService, View view, Session session) {
        super();
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        User user = this.session.getSecuredUser();
        this.sortByDate(this.planService.subscribedPlans(user))
                .forEach(this.view::showPlans);
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
