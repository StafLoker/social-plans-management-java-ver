package org.stafloker.data.daos.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.daos.jpa.entities.spm.PlanEntity;
import org.stafloker.data.daos.jpa.persistences.PlanPersistenceJpa;
import org.stafloker.data.models.spm.Plan;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryJpa implements PlanRepository {

    private final PlanPersistenceJpa planPersistenceJpa;

    @Autowired
    public PlanRepositoryJpa(PlanPersistenceJpa planPersistenceJpa) {
        this.planPersistenceJpa = planPersistenceJpa;
    }

    @Override
    public Plan create(Plan entity) {
        return this.planPersistenceJpa.save(new PlanEntity(entity)).toPlan();
    }

    @Override
    public Plan update(Plan entity) {
        return this.planPersistenceJpa.save(new PlanEntity(entity)).toPlan();
    }

    @Override
    public Optional<Plan> read(Long id) {
        return this.planPersistenceJpa.findById(id).map(PlanEntity::toPlan);
    }

    @Override
    public void deleteById(Long id) {
        this.planPersistenceJpa.deleteById(id);
    }

    @Override
    public List<Plan> saveAll(List<Plan> entitiesList) {
        List<PlanEntity> entitiesToSave = entitiesList.stream()
                .map(PlanEntity::new)
                .toList();

        List<PlanEntity> savedEntities = this.planPersistenceJpa.saveAll(entitiesToSave);

        return savedEntities.stream()
                .map(PlanEntity::toPlan)
                .toList();
    }

    @Override
    public void deleteAll() {
        this.planPersistenceJpa.deleteAll();
    }

    @Override
    public List<Plan> findAll() {
        return this.planPersistenceJpa.findAll().stream().map(PlanEntity::toPlan).toList();
    }
}
