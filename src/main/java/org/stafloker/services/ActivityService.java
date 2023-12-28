package org.stafloker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.daos.ActivityRepository;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity create(Activity activity) {
        return this.activityRepository.create(activity);
    }
}
