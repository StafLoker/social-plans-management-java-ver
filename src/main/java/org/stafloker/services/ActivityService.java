package org.stafloker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.activityTypes.Cinema;
import org.stafloker.data.models.spm.activityTypes.Theater;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity create(String type, String name, String description, Integer duration, Double price, Integer capacity) {
        return this.activityRepository.create(this.createActivityBasedOnType(type, name, description, duration, price, capacity));
    }

    private Activity createActivityBasedOnType(String type, String name, String description, Integer duration, Double price, Integer capacity) {
        Activity.ActivityBuilder<?, ?> builder = switch (type) {
            case "Theater" -> Theater.builder();
            case "Cinema" -> Cinema.builder();
            case "Generic" -> Activity.builder();
            default -> throw new IllegalArgumentException("Unsupported activity type: " + type);
        };
        builder.name(name).description(description).duration(duration).price(price);
        if (capacity != null) {
            builder.capacity(capacity);
        }
        return builder.build();
    }
}
