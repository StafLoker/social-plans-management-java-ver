package org.stafloker.data.repositories.repositories_sql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.msp.Activity;
import org.stafloker.data.repositories.ActivityRepository;

@Repository
public class ActivityRepositorySql extends GenericRepositorySql<Activity> implements ActivityRepository {

    @Autowired
    public ActivityRepositorySql(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Activity> getEntityClass() {
        return Activity.class;
    }
}
