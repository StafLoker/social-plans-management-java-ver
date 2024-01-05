package org.stafloker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.console.Session;
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
        Plan plan = this.planService.create(Plan.builder().name("Test").date(LocalDateTime.of(2023, 12, 1, 12, 30)).meetingPlace("Sol, Madrid").build(), this.session.getSecuredUser());
        assertEquals("Test", plan.getName());
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testDelete() {
        this.planService.delete(7L, this.session.getSecuredUser());
        assertTrue(this.planRepository.read(7L).isEmpty());

        this.session.setLoggedUser(this.userRepository.read(2L).get());
        assertThrows(SecurityProhibitionException.class, ()-> this.planService.delete(1L, this.session.getSecuredUser()));
    }

    @Test
    void testAddActivity() {
        this.planService.addActivity(2L, 3L, this.session.getSecuredUser());
        assertEquals(600, this.planRepository.read(2L).get().getDuration());
    }

    @Test
    void testEnrollSubscriber() {
        this.session.setLoggedUser(this.userRepository.read(6L).get());
        this.planService.enrollSubscriber(3L, this.session.getSecuredUser());
        assertTrue(this.planRepository.read(3L).get().getActivitiesList().contains(this.userRepository.read(2L).get()));
        this.planRepository.read(3L).get().getActivitiesList().remove(this.userRepository.read(1L).get());
        this.session.setLoggedUser(this.userRepository.read(1L).get());

        this.planService.enrollSubscriber(4L, this.session.getSecuredUser());
        assertThrows(InvalidAttributeException.class, ()-> this.planService.enrollSubscriber(5L, this.session.getSecuredUser()));
        assertThrows(DuplicateException.class, ()->this.planService.enrollSubscriber(4L, this.session.getSecuredUser()));
        this.planRepository.read(4L).get().getActivitiesList().remove(this.userRepository.read(1L).get());

        Plan plan = this.planService.create(Plan.builder().name("Test, past plan").date((LocalDateTime.now().plusDays(-1))).meetingPlace("Callao, Madrid").build(), this.session.getSecuredUser());
        assertThrows(InvalidAttributeException.class, ()-> this.planService.enrollSubscriber(plan.getId(), this.session.getSecuredUser()));
        this.planService.delete(plan.getId(), this.session.getSecuredUser());
    }

    @Test
    void testPrice() {
        assertThrows(SecurityProhibitionException.class, ()-> this.planService.price(5L, this.session.getSecuredUser()));
    }

    @Test
    void testDuration() {
        assertThrows(SecurityProhibitionException.class, ()-> this.planService.duration(5L, this.session.getSecuredUser()));
    }


    @Test
    void testAvailablePlans() {
        Plan planID1 = this.planRepository.read(1L).get();
        assertTrue(this.planService.availablePlans().contains(planID1));

        Plan plan = this.planService.create(Plan.builder().name("Test, past plan").date((LocalDateTime.now().plusDays(-1))).meetingPlace("Callao, Madrid").build(), this.session.getSecuredUser());
        assertFalse(this.planService.availablePlans().contains(plan));
        this.planService.delete(plan.getId(), this.session.getSecuredUser());
    }

    @Test
    void testSubscribedPlans() {
        List<Plan> subscribedPlansList = new LinkedList<>();
        subscribedPlansList.add(this.planRepository.read(1L).get());
        subscribedPlansList.add(this.planRepository.read(2L).get());
        subscribedPlansList.add(this.planRepository.read(3L).get());
        assertEquals(subscribedPlansList, this.planService.subscribedPlans(this.session.getSecuredUser()));
        assertFalse(subscribedPlansList.contains(this.planRepository.read(4L).get()));
    }

    @Test
    void testPriceRangePlans() {
        assertTrue(this.planService.priceRangePlans(40.0, 8.0, this.session.getSecuredUser()).contains(this.planRepository.read(1L).get()));
    }

    @Test
    void testWeekendPlans() {
        Plan plan = this.planService.create(Plan.builder().name("Test, weekend plan").date(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(0).withMinute(0).withSecond(0)).meetingPlace("Sol, Madrid").capacity(10).build(), this.session.getSecuredUser());
        assertTrue(this.planService.weekendPlans().contains(plan));
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testPlansWithKeyword() {
        List<Plan> list = new ArrayList<>();
        list.add(this.planRepository.read(1L).get());
        list.add(this.planRepository.read(3L).get());
        list.add(this.planRepository.read(4L).get());
        assertEquals(list, this.planService.plansContainingKeyword("Cinema"));
    }
}