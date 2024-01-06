package org.stafloker.data.models.spm.activityTypes;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.stafloker.data.models.spm.Activity;

import jakarta.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Theater")
public class Theater extends Activity {
    private static final double YOUTH_THEATER_DISCOUNT = 0.50;
    private static final double SENIOR_CITIZEN_THEATER_DISCOUNT = 0.70;

    private static final int MAX_YOUTH_THEATER_DISCOUNT_AGE = 25;
    private static final int MIN_SENIOR_CITIZEN_THEATER_DISCOUNT_AGE = 65;

    @Override
    public Double getPrice(Integer age) {
        if (age <= MAX_YOUTH_THEATER_DISCOUNT_AGE) {
            return this.getPrice() - this.getPrice() * YOUTH_THEATER_DISCOUNT;
        } else if (age >= MIN_SENIOR_CITIZEN_THEATER_DISCOUNT_AGE) {
            return this.getPrice() - this.getPrice() * SENIOR_CITIZEN_THEATER_DISCOUNT;
        }
        return this.getPrice();
    }
}
