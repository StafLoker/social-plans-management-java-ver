package org.stafloker.console.commands.planCommands.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stafloker.console.Session;
import org.stafloker.console.Command;
import org.stafloker.console.View;
import org.stafloker.console.exceptions.UnsupportedAttributesException;
import org.stafloker.data.models.User;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.services.PlanService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
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
        User user = this.session.getSecuredUser();
        if (values.length < 3 || values.length > 4) {
            throw new UnsupportedAttributesException(this.helpParameters());
        }
        Plan createdPlan;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
        if (values.length == 4) {
            createdPlan = this.planService.create(Plan.builder()
                    .name(values[0])
                    .date(LocalDateTime.parse(values[1], formatter))
                    .meetingPlace(values[2])
                    .capacity(Integer.parseInt(values[3]))
                    .build(), user);
        } else {
            createdPlan = this.planService.create(Plan.builder()
                    .name(values[0])
                    .date(LocalDateTime.parse(values[1], formatter))
                    .meetingPlace(values[2])
                    .build(), user);
        }
        this.view.showPlan(createdPlan);
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
