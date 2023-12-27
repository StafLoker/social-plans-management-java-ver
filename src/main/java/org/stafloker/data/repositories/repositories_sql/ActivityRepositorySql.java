package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.msp.Activity;
import org.stafloker.data.repositories.ActivityRepository;

@Repository
public class ActivityRepositorySql extends GenericRepositorySql<Activity> implements ActivityRepository {

    public ActivityRepositorySql(Session session) {
        super(session);
    }

    @Override
    protected Class<Activity> getEntityClass() {
        return Activity.class;
    }
}
