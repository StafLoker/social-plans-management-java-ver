package org.stafloker.data.models.msp.activityType;

import org.stafloker.data.models.msp.Activity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Cinema")
public class Cinema extends Activity {
    private static final int MAX_YOUTH_MOVIE_DISCOUNT_AGE = 21;
    private static final double YOUTH_MOVIE_DISCOUNT = 0.50;

    @Override
    public Double getPrice(Integer age) {
        return age <= MAX_YOUTH_MOVIE_DISCOUNT_AGE ? this.getPrice() - this.getPrice() * YOUTH_MOVIE_DISCOUNT : this.getPrice();
    }
}
