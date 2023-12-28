package org.stafloker.data.daos.repositories_sql;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.daos.ActivityRepository;

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
