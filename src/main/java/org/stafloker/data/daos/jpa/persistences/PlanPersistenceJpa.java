package org.stafloker.data.daos.jpa.persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stafloker.data.models.spm.Plan;

public interface PlanPersistenceJpa extends JpaRepository<Plan, Long> {
}
