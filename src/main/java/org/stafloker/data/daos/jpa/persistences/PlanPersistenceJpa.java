package org.stafloker.data.daos.jpa.persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stafloker.data.daos.jpa.entities.spm.PlanEntity;

public interface PlanPersistenceJpa extends JpaRepository<PlanEntity, Long> {
}
