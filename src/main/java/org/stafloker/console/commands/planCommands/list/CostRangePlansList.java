package org.stafloker.console.commands.planCommands.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;

import org.stafloker.console.commands.planCommands.PlanCommand;
import org.stafloker.services.PlanService;

@Component
public class CostRangePlansList extends PlanCommand implements Command {
    private static final String VALUE = "price-range-plans";
    private static final String PARAMETER_HELP = "<price>;<range>";
    private static final String COMMENT_HELP = "Shows plans with a cost between price ± range";

    private final Session session;
    private final PlanService planService;
    private final View view;

    @Autowired
    public CostRangePlansList(PlanService planService, View view, Session session) {
        super();
        this.planService = planService;
        this.view = view;
        this.session = session;
    }

    @Override
    public void execute(String[] values) {
        this.session.assertLogin();
        if (values.length != 2) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        this.sortByDate(this.planService.costRangePlans(Double.parseDouble(values[0]), Double.parseDouble(values[1]), this.session.getSecuredUser()))
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
