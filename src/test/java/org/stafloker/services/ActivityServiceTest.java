package org.stafloker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.stafloker.TestConfig;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.UserRepository;
import org.stafloker.data.models.spm.Activity;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ActivityServiceTest {

    @Autowired
    private Session session;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setup() {
        this.session.setLoggedUser(this.userRepository.read(1L).get());
    }

    @Test
    void testCreate() {
        Activity activity = this.activityService.create(Activity.builder().name("Test").description("").duration(300).price(0.0).build());
        assertEquals("Test", activity.getName());
        this.activityRepository.deleteById(activity.getId());
    }
}