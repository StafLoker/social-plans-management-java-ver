package org.stafloker.data.models.spm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class PlanTest {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddSubscriber() {
        Plan plan = this.planRepository.read(3L).get();
        plan.addSubscriber(this.userRepository.read(1L).get());
        assertTrue(plan.getSubscribersList().contains(this.userRepository.read(1L).get()));
        plan.getSubscribersList().remove(this.userRepository.read(1L).get());

        Plan plan2 = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(1)).meetingPlace("Callao, Madrid").capacity(1).build();
        plan2.addSubscriber(this.userRepository.read(1L).get());
        assertThrows(InvalidAttributeException.class, () -> plan2.addSubscriber(this.userRepository.read(2L).get()));

        Plan plan3 = this.planRepository.read(5L).get();
        plan3.addSubscriber(this.userRepository.read(1L).get());
        assertTrue(plan3.getSubscribersList().contains(this.userRepository.read(1L).get()));
        plan3.getSubscribersList().remove(this.userRepository.read(1L).get());
    }

    @Test
    void testAddActivity() {
        Plan plan = this.planRepository.read(1L).get();
        plan.addActivity(this.activityRepository.read(1L).get());
        this.planRepository.update(plan);
        assertThrows(InvalidAttributeException.class, () -> this.planRepository.read(1L).get().addActivity(this.activityRepository.read(1L).get()));
        plan.getActivitiesList().remove(this.activityRepository.read(1L).get());
        this.planRepository.update(plan);

        plan = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(1)).meetingPlace("Callao, Madrid").capacity(60).build();
        plan.addActivity(this.activityRepository.read(2L).get());
        assertEquals(20, plan.getCapacity());

        plan.addActivity(this.activityRepository.read(1L).get());
        assertEquals(20, plan.getCapacity());
    }

    @Test
    void testGetAvailableSpots() {
        assertEquals(15, this.planRepository.read(2L).get().getAvailableSpots());
    }

    @Test
    void testGetPrice() {
        assertEquals(47.75, this.planRepository.read(1L).get().getPrice(this.userRepository.read(1L).get()));

    }

    @Test
    void testGetDuration() {
        assertEquals(510, this.planRepository.read(1L).get().getDuration());
    }

    @Test
    void testSetCapacity() {
        assertThrows(InvalidAttributeException.class, () -> this.planRepository.read(1L).get().setCapacity(0));
    }
}