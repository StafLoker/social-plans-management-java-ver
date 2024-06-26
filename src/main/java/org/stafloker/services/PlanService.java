package org.stafloker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.models.User;
import org.stafloker.data.models.exceptions.InvalidAttributeException;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.services.exceptions.DuplicateException;
import org.stafloker.services.exceptions.NotFoundException;
import org.stafloker.services.exceptions.SecurityProhibitionException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final ActivityRepository activityRepository;
    private final Session session;

    @Autowired
    public PlanService(PlanRepository planRepository, ActivityRepository activityRepository, Session session) {
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
        this.session = session;
    }

    public Plan create(String name, LocalDateTime date, String meetingPlace, Integer capacity) {
        Plan.PlanBuilder builder = Plan.builder().name(name).owner(this.session.getSecuredUser()).date(date).meetingPlace(meetingPlace);
        if (capacity != null) {
            builder.capacity(capacity);
        }
        return this.planRepository.create(builder.build());
    }

    public void delete(Long planId) {
        Optional<Plan> plan = this.planRepository.read(planId);
        if (plan.isPresent()) {
            if (!plan.get().getOwner().equals(this.session.getSecuredUser())) {
                throw new SecurityProhibitionException("To delete the plan with id: " + plan.get().getId());
            }
            this.planRepository.deleteById(planId);
        }
    }

    public Plan addActivity(Long planId, Long activityId) {
        Optional<Plan> plan = this.planRepository.read(planId);
        if (plan.isEmpty()) {
            throw new NotFoundException("The plan with ID: " + planId);
        }
        if (!plan.get().getOwner().equals(this.session.getSecuredUser())) {
            throw new SecurityProhibitionException("To add activities to the plan with ID: " + plan.get().getId() +
                    " you must be the owner; the owner is: " + plan.get().getOwner().getName());
        }
        Optional<Activity> activity = this.activityRepository.read(activityId);
        if (activity.isEmpty()) {
            throw new NotFoundException("The activity with ID: " + activityId);
        }
        plan.get().addActivity(activity.get());
        return this.planRepository.update(plan.get());
    }

    public Plan enrollSubscriber(Long planId) {
        Optional<Plan> plan = this.planRepository.read(planId);
        if (plan.isEmpty()) {
            throw new NotFoundException("The plan with ID: " + planId);
        }
        if (plan.get().getDate().isBefore(LocalDateTime.now())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");
            throw new InvalidAttributeException("The plan with ID: " + plan.get().getId() + " was conducted on: " + plan.get().getDate().format(formatter));
        }
        User user = this.session.getSecuredUser();
        if (plan.get().getSubscribers().contains(user)) {
            throw new DuplicateException("You cannot join a plan twice, ID introduced: " + plan.get().getId());
        }
        checkNoTimeCollisionBetweenSubscribedPlans(plan.get(), user);
        plan.get().addSubscriber(user);
        return this.planRepository.update(plan.get());
    }

    public double price(Long planId) {
        Optional<Plan> plan = this.planRepository.read(planId);
        if (plan.isEmpty()) {
            throw new NotFoundException("Plan with ID: " + planId);
        }
        User user = this.session.getSecuredUser();
        if (!plan.get().getSubscribers().contains(user)) {
            throw new SecurityProhibitionException("You cannot check the price of a plan you are not participating in, ID introduced: " + plan.get().getId());
        }
        return plan.get().price(user);
    }

    public int duration(Long planId) {
        Optional<Plan> plan = this.planRepository.read(planId);
        if (plan.isEmpty()) {
            throw new InvalidAttributeException("Plan with ID: " + planId);
        }
        if (!plan.get().getSubscribers().contains(this.session.getSecuredUser())) {
            throw new SecurityProhibitionException("You cannot check the duration of a plan you are not participating in, ID introduced: " + plan.get().getId());
        }
        return plan.get().duration();
    }

    public List<Plan> availablePlans() {
        return this.planRepository.findAll().stream()
                .filter(plan -> plan.getDate().isAfter(LocalDateTime.now()))
                .toList();
    }

    public List<Plan> subscribedPlans() {
        return this.planRepository.findAll().stream()
                .filter(plan -> plan.getSubscribers().contains(this.session.getSecuredUser()))
                .toList();
    }

    public List<Plan> priceRangePlans(Double price, Double range) {
        if (price < range) {
            throw new InvalidAttributeException("The price " + price + " cannot be less than the range " + range);
        }
        User user = this.session.getSecuredUser();
        return this.availablePlans().stream()
                .filter(plan -> plan.price(user) >= price - range && plan.price(user) <= Math.min(price + range, Integer.MAX_VALUE))
                .toList();
    }

    public List<Plan> weekendPlans() {
        LocalDateTime nextEndOfFriday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).withHour(23).withMinute(59).withSecond(59);
        LocalDateTime nextMonday = nextEndOfFriday.plusDays(2).plusSeconds(1);
        return this.availablePlans().stream()
                .filter(plan -> plan.getDate().isAfter(nextEndOfFriday) && plan.getDate().isBefore(nextMonday))
                .toList();
    }

    public List<Plan> plansContainingKeyword(String keyword) {
        return this.availablePlans().stream()
                .filter(plan -> plan.getActivities().stream()
                        .anyMatch(activity -> activity.getName().contains(keyword) || activity.getDescription().contains(keyword)))
                .toList();
    }

    private void checkNoTimeCollisionBetweenSubscribedPlans(Plan plan, User user) {
        this.planRepository.findAll().stream()
                .filter(pl -> pl.getSubscribers().contains(user))
                .filter(pl -> pl.getDate().isBefore(plan.getDate().plusMinutes(plan.duration())) && pl.getDate().plusMinutes(pl.duration()).isAfter(plan.getDate()))
                .findAny()
                .ifPresent(pl -> {
                    throw new InvalidAttributeException("You cannot join the plan with ID: " + plan.getId() + " because it overlaps with plan ID: " + pl.getId());
                });
    }
}