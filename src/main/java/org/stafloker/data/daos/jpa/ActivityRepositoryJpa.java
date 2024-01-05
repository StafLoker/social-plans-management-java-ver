package org.stafloker.data.daos.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.jpa.persistences.ActivityPersistenceJpa;
import org.stafloker.data.models.spm.Activity;

import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepositoryJpa implements ActivityRepository {

    private final ActivityPersistenceJpa activityPersistenceJpa;

    @Autowired
    public ActivityRepositoryJpa(ActivityPersistenceJpa activityPersistenceJpa) {
        this.activityPersistenceJpa = activityPersistenceJpa;
    }

    @Override
    public Activity create(Activity entity) {
        return this.activityPersistenceJpa.save(entity);
    }

    @Override
    public Activity update(Activity entity) {
        return this.activityPersistenceJpa.save(entity);
    }

    @Override
    public Optional<Activity> read(Long id) {
        return this.activityPersistenceJpa.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.activityPersistenceJpa.deleteById(id);
    }

    @Override
    public List<Activity> saveAll(List<Activity> entitiesList) {
        return this.activityPersistenceJpa.saveAll(entitiesList);
    }

    @Override
    public void deleteAll() {
        this.activityPersistenceJpa.deleteAll();
    }

    @Override
    public List<Activity> findAll() {
        return this.activityPersistenceJpa.findAll();
    }
}
