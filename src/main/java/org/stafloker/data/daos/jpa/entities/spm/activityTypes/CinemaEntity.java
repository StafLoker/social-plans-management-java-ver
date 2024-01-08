package org.stafloker.data.daos.jpa.entities.spm.activityTypes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.stafloker.data.daos.jpa.entities.spm.ActivityEntity;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Cinema")
public class CinemaEntity extends ActivityEntity {
}
