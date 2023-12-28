package org.stafloker.console.commands.planCommands;

import org.stafloker.data.models.spm.Plan;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class PlanCommand {
    protected List<Plan> sortByDate(List<Plan> planList) {
        List<Plan> mutableList = new LinkedList<>(planList);
        mutableList.sort(Comparator.comparing(Plan::getDate));
        return mutableList;
    }
}
