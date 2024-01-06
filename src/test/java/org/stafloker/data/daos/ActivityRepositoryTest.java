package org.stafloker.data.daos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.activityTypes.Theater;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testCreate() {
        Activity activity = this.activityRepository.create(Theater.builder().name("0-Theater").description("Watch theater in Madrid").duration(180).price(10.5).build());
        assertTrue(this.activityRepository.read(activity.getId()).isPresent());
        this.activityRepository.deleteById(activity.getId());
    }

    @Test
    void testUpdate() {
        Activity activity = this.activityRepository.create(Theater.builder().name("0-Theater").description("Watch theater in Madrid").duration(180).price(10.5).build());
        activity.setName("test");
        this.activityRepository.update(activity);
        assertEquals("test", this.activityRepository.read(activity.getId()).get().getName());
        this.activityRepository.deleteById(activity.getId());
    }

    @Test
    void testRead() {
        assertTrue(this.activityRepository.read(1L).isPresent());

        assertFalse(this.activityRepository.read(50L).isPresent());
    }

    @Test
    void testDelete() {
        Activity activity = this.activityRepository.create(Theater.builder().name("0-Theater").description("Watch theater in Madrid").duration(180).price(10.5).build());
        this.activityRepository.deleteById(activity.getId());
        assertFalse(this.activityRepository.read(activity.getId()).isPresent());
    }

    @Test
    void testGetAll() {
        Activity activity = this.activityRepository.create(Theater.builder().name("0-Theater").description("Watch theater in Madrid").duration(180).price(10.5).build());
        List<Activity> list = this.activityRepository.findAll();
        assertTrue(list.contains(activity));
        this.activityRepository.deleteById(activity.getId());
    }


}