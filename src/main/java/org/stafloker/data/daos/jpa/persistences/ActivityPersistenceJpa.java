package org.stafloker.data.daos.jpa.persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stafloker.data.models.spm.Activity;

public interface ActivityPersistenceJpa extends JpaRepository<Activity, Long> {
}
