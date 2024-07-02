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
        Plan plan = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(1)).meetingPlace("Callao, Madrid").capacity(1).build();
        plan.addSubscriber(this.userRepository.read(1L).get());
        assertThrows(InvalidAttributeException.class, () -> plan.addSubscriber(this.userRepository.read(2L).get()));

        Plan plan2 = this.planRepository.read(5L).get();
        plan2.addSubscriber(this.userRepository.read(1L).get());
        assertTrue(plan2.getSubscribers().contains(this.userRepository.read(1L).get()));
        plan2.getSubscribers().remove(this.userRepository.read(1L).get());
    }

    @Test
    void testAddActivity() {
        Plan plan = this.planRepository.read(1L).get();
        plan.addActivity(this.activityRepository.read(1L).get());
        this.planRepository.update(plan);
        assertThrows(InvalidAttributeException.class, () -> this.planRepository.read(1L).get().addActivity(this.activityRepository.read(1L).get()));
        plan.getActivities().remove(this.activityRepository.read(1L).get());
        this.planRepository.update(plan);

        plan = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(1)).meetingPlace("Callao, Madrid").capacity(60).build();
        plan.addActivity(this.activityRepository.read(2L).get());
        assertEquals(20, plan.getCapacity());

        plan.addActivity(this.activityRepository.read(1L).get());
        assertEquals(20, plan.getCapacity());
    }

    @Test
    void testAvailableSpots() {
        assertEquals(15, this.planRepository.read(2L).get().availableSpots());
    }

    @Test
    void testPrice() {
        assertEquals(47.75, this.planRepository.read(1L).get().price(this.userRepository.read(1L).get()));
    }

    @Test
    void testDuration() {
        assertEquals(510, this.planRepository.read(1L).get().duration());
    }
}