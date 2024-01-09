package org.stafloker.data.daos.jpa.entities.spm.activityTypes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.daos.jpa.entities.spm.ActivityEntity;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.activityTypes.Cinema;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Cinema")
public class CinemaEntity extends ActivityEntity {
    public CinemaEntity(Cinema cinema) {
        super(cinema);
    }

    @Override
    public Activity toActivity() {
        Cinema cinema = new Cinema();
        BeanUtils.copyProperties(this, cinema);
        return cinema;
    }
}
