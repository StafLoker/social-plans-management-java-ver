package org.stafloker.data.daos.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.jpa.entities.spm.ActivityEntity;
import org.stafloker.data.daos.jpa.entities.spm.activityTypes.CinemaEntity;
import org.stafloker.data.daos.jpa.entities.spm.activityTypes.TheaterEntity;
import org.stafloker.data.daos.jpa.persistences.ActivityPersistenceJpa;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.activityTypes.Cinema;
import org.stafloker.data.models.spm.activityTypes.Theater;

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
        return this.activityPersistenceJpa.save(this.mapToActivityEntity(entity)).toActivity();
    }

    @Override
    public Activity update(Activity entity) {
        return this.activityPersistenceJpa.save(this.mapToActivityEntity(entity)).toActivity();
    }

    @Override
    public Optional<Activity> read(Long id) {
        return this.activityPersistenceJpa.findById(id).map(ActivityEntity::toActivity);
    }

    @Override
    public void deleteById(Long id) {
        this.activityPersistenceJpa.deleteById(id);
    }

    @Override
    public List<Activity> saveAll(List<Activity> entitiesList) {
        List<ActivityEntity> entitiesToSave = entitiesList.stream()
                .map(this::mapToActivityEntity)
                .toList();

        List<ActivityEntity> savedEntities = this.activityPersistenceJpa.saveAll(entitiesToSave);

        return savedEntities.stream()
                .map(ActivityEntity::toActivity)
                .toList();
    }

    @Override
    public void deleteAll() {
        this.activityPersistenceJpa.deleteAll();
    }

    @Override
    public List<Activity> findAll() {
        return this.activityPersistenceJpa.findAll().stream().map(ActivityEntity::toActivity).toList();
    }

    private ActivityEntity mapToActivityEntity(Activity activity) {
        if (activity instanceof Cinema) {
            return new CinemaEntity((Cinema) activity);
        } else if (activity instanceof Theater) {
            return new TheaterEntity((Theater) activity);
        } else {
            return new ActivityEntity(activity);
        }
    }
}
