package org.stafloker.data.models.spm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ActivityTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void getPrice() {
        assertEquals(5.25, this.activityRepository.read(1L).get().getPrice(17));
        assertEquals(15.5, this.activityRepository.read(2L).get().getPrice(17));
        assertEquals(5.75, this.activityRepository.read(3L).get().getPrice(17));
        assertEquals(11.5, this.activityRepository.read(3L).get().getPrice(22));
    }
}