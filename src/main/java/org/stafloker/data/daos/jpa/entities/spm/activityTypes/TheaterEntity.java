package org.stafloker.data.daos.jpa.entities.spm.activityTypes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.daos.jpa.entities.spm.ActivityEntity;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.activityTypes.Theater;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Theater")
public class TheaterEntity extends ActivityEntity {

    public TheaterEntity(Theater theater) {
        super(theater);
    }

    @Override
    public Activity toActivity() {
        Theater theater = new Theater();
        BeanUtils.copyProperties(this, theater);
        return theater;
    }
}
