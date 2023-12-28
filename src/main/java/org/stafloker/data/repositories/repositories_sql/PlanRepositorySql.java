package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.data.repositories.PlanRepository;

@Repository
public class PlanRepositorySql extends GenericRepositorySql<Plan> implements PlanRepository {

    @Autowired
    public PlanRepositorySql(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Plan> getEntityClass() {
        return Plan.class;
    }
}
