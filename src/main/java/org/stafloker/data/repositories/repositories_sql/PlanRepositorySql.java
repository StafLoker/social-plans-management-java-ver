package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.msp.Plan;
import org.stafloker.data.repositories.PlanRepository;

@Repository
public class PlanRepositorySql extends GenericRepositorySql<Plan> implements PlanRepository {
    public PlanRepositorySql(Session session) {
        super(session);
    }

    @Override
    protected Class<Plan> getEntityClass() {
        return Plan.class;
    }
}
