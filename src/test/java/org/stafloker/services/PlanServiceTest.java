package org.stafloker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.exceptions.InvalidAttributeException;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.services.exceptions.DuplicateException;
import org.stafloker.services.exceptions.SecurityProhibitionException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class PlanServiceTest {

    @Autowired
    private Session session;
    @Autowired
    private PlanService planService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        this.session.setLoggedUser(this.userRepository.read(1L).get());
    }

    @Test
    void testCreate() {
        Plan plan = this.planService.create("Test", LocalDateTime.of(2023, 12, 1, 12, 30), "Sol, Madrid", null);
        assertEquals("Test", plan.getName());
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testDelete() {
        this.planService.delete(7L);
        assertTrue(this.planRepository.read(7L).isEmpty());

        this.session.setLoggedUser(this.userRepository.read(2L).get());
        assertThrows(SecurityProhibitionException.class, () -> this.planService.delete(1L));
    }

    @Test
    void testAddActivity() {
        this.planService.addActivity(2L, 3L);
        assertEquals(600, this.planRepository.read(2L).get().duration());
        Plan plan = this.planRepository.read(2L).get();
        plan.getActivities().removeIf(activity -> activity.getId().equals(3L));
        this.planRepository.update(plan);
    }

    @Test
    void testEnrollSubscriber() {
        this.session.setLoggedUser(this.userRepository.read(6L).get());
        this.planService.enrollSubscriber(3L);
        assertTrue(this.planRepository.read(3L).get().getSubscribers().contains(this.userRepository.read(2L).get()));
        this.planRepository.read(3L).get().getSubscribers().remove(this.userRepository.read(1L).get());
        this.session.setLoggedUser(this.userRepository.read(1L).get());

        this.planService.enrollSubscriber(4L);
        assertThrows(InvalidAttributeException.class, () -> this.planService.enrollSubscriber(5L));
        assertThrows(DuplicateException.class, () -> this.planService.enrollSubscriber(4L));
        Plan tempPlan = this.planRepository.read(4L).get();
        tempPlan.getSubscribers().remove(this.session.getSecuredUser());
        this.planRepository.update(tempPlan);

        Plan plan = this.planService.create("Test, past plan", (LocalDateTime.now().plusDays(-1)), "Callao, Madrid", null);
        assertThrows(InvalidAttributeException.class, () -> this.planService.enrollSubscriber(plan.getId()));
        this.planService.delete(plan.getId());
    }

    @Test
    void testPrice() {
        assertThrows(SecurityProhibitionException.class, () -> this.planService.price(5L));
    }

    @Test
    void testDuration() {
        assertThrows(SecurityProhibitionException.class, () -> this.planService.duration(5L));
    }


    @Test
    void testAvailablePlans() {
        Plan planID1 = this.planRepository.read(1L).get();
        assertTrue(this.planService.availablePlans().contains(planID1));

        Plan plan = this.planService.create("Test, past plan", (LocalDateTime.now().plusDays(-1)), "Callao, Madrid", null);
        assertFalse(this.planService.availablePlans().contains(plan));
        this.planService.delete(plan.getId());
    }

    @Test
    void testSubscribedPlans() {
        List<Plan> subscribedPlansList = new LinkedList<>();
        subscribedPlansList.add(this.planRepository.read(1L).get());
        subscribedPlansList.add(this.planRepository.read(2L).get());
        subscribedPlansList.add(this.planRepository.read(3L).get());
        assertEquals(subscribedPlansList, this.planService.subscribedPlans());
        assertFalse(this.planService.subscribedPlans().contains(this.planRepository.read(4L).get()));
    }

    @Test
    void testPriceRangePlans() {
        assertTrue(this.planService.priceRangePlans(40.0, 8.0).contains(this.planRepository.read(1L).get()));
    }

    @Test
    void testWeekendPlans() {
        Plan plan = this.planService.create("Test, weekend plan", LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(7).withMinute(0).withSecond(0), "Sol, Madrid", 10);
        assertTrue(this.planService.weekendPlans().contains(plan));
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testPlansWithKeyword() {
        List<Plan> list = new ArrayList<>();
        list.add(this.planRepository.read(1L).get());
        list.add(this.planRepository.read(3L).get());
        list.add(this.planRepository.read(4L).get());
        assertEquals(list, this.planService.plansContainingKeyword("movie"));
    }
}