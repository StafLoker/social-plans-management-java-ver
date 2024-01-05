package org.stafloker.data.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.models.spm.Plan;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class PlanRepositoryTest {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreate() {
        Plan plan = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(93).plusHours(24).plusMinutes(60)).meetingPlace("Sol, Madrid").build();
        plan.setOwner(this.userRepository.read(1L).get());
        plan = this.planRepository.create(plan);
        assertTrue(this.planRepository.read(plan.getId()).isPresent());
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testUpdate() {
        Plan plan = this.planRepository.read(1L).get();
        plan.setName("Test");
        this.planRepository.update(plan);
        assertEquals("Test", this.planRepository.read(plan.getId()).get().getName());
        plan.setName("Plan0, noon, pizza + cinema + beers");
        this.planRepository.update(plan);
    }

    @Test
    void testRead() {
        assertTrue(this.planRepository.read(1L).isPresent());

        assertFalse(this.planRepository.read(50L).isPresent());
    }

    @Test
    void testDelete() {
        Plan plan = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(93).plusHours(24).plusMinutes(60)).meetingPlace("Sol, Madrid").build();
        plan.setOwner(this.userRepository.read(1L).get());
        plan = this.planRepository.create(plan);
        this.planRepository.deleteById(plan.getId());
        assertFalse(this.planRepository.read(plan.getId()).isPresent());
        this.planRepository.deleteById(plan.getId());
    }

    @Test
    void testGetAll() {
        Plan temp = Plan.builder().name("Test").date(LocalDateTime.now().plusDays(93).plusHours(24).plusMinutes(60)).meetingPlace("Sol, Madrid").build();
        temp.setOwner(this.userRepository.read(1L).get());
        Plan plan = this.planRepository.create(temp);
        List<Plan> list = this.planRepository.findAll();
        assertTrue(list.contains(plan));
        assertTrue(list.contains(this.planRepository.read(1L).get()));
        this.planRepository.deleteById(plan.getId());
    }

}