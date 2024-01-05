package org.stafloker.data.daos.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.daos.jpa.persistences.PlanPersistenceJpa;
import org.stafloker.data.models.spm.Plan;

import java.util.List;
import java.util.Optional;

public class PlanRepositoryJpa implements PlanRepository {

    private final PlanPersistenceJpa planPersistenceJpa;

    @Autowired
    public PlanRepositoryJpa( PlanPersistenceJpa planPersistenceJpa) {
        this.planPersistenceJpa = planPersistenceJpa;
    }

    @Override
    public Plan create(Plan entity) {
        return this.planPersistenceJpa.save(entity);
    }

    @Override
    public Plan update(Plan entity) {
        return this.planPersistenceJpa.save(entity);
    }

    @Override
    public Optional<Plan> read(Long id) {
        return this.planPersistenceJpa.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.planPersistenceJpa.deleteById(id);
    }

    @Override
    public List<Plan> saveAll(List<Plan> entitiesList) {
        return this.planPersistenceJpa.saveAll(entitiesList);
    }

    @Override
    public void deleteAll() {
        this.planPersistenceJpa.deleteAll();
    }

    @Override
    public List<Plan> findAll() {
        return this.planPersistenceJpa.findAll();
    }
}
