package org.stafloker.data.daos.seeders;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.stafloker.data.models.spm.Activity;
import org.stafloker.data.models.spm.Plan;
import org.stafloker.data.models.spm.activityType.Cinema;
import org.stafloker.data.models.spm.activityType.Theater;
import org.stafloker.data.daos.ActivityRepository;
import org.stafloker.data.daos.PlanRepository;
import org.stafloker.data.daos.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Random;

@Repository
@Profile({"dev", "test"})
public class SpmSeederServiceDev {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public SpmSeederServiceDev(UserRepository userRepository, PlanRepository planRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
    }

    public void seedDatabase() {
        LogManager.getLogger(this.getClass()).warn("------- Spm Initial Load --------");
        Activity[] activities = {
                Theater.builder().name("0-Theater").description("Watch theater in Madrid").duration(180).price(10.5).build(),
                Activity.builder().name("1-Musical").description("Watch musical in Madrid").duration(180).price(15.5).capacity(20).build(),
                Cinema.builder().name("2-Movie").description("Watch comedy movie in Madrid").duration(180).price(11.5).capacity(50).build(),
                Cinema.builder().name("3-Movie").description("Watch horror movie in Madrid").duration(180).price(10.5).capacity(40).build(),
                Activity.builder().name("4-Drinks").description("Go to a bar in Cava Baja").duration(150).price(30.0).capacity(15).build(),
                Activity.builder().name("5-Pizza").description("Go for pizza in Madrid").duration(120).price(12.0).capacity(22).build(),
                Activity.builder().name("6-Party").description("Go clubbing in Madrid").duration(300).price(30.0).capacity(40).build(),
        };
        this.activityRepository.saveAll(Arrays.asList(activities));

        LogManager.getLogger(this.getClass()).warn("        ------- activities");

        Random random = new Random();

        Plan[] plans = {
                Plan.builder().name("0-Plan, lunchtime, pizza + movie + drinks").date(LocalDateTime.now().plusDays(random.nextInt(93)).plusHours(random.nextInt(24)).plusMinutes(60)).meetingPlace("Sol, Madrid").build(),
                Plan.builder().name("1-Plan, morning, full theater").date(LocalDateTime.now().plusDays(random.nextInt(93)).plusHours(random.nextInt(24)).plusMinutes(60)).meetingPlace("Gran Via, Madrid").capacity(30).build(),
                Plan.builder().name("2-Plan, comedy").date(LocalDateTime.now().plusDays(random.nextInt(93)).plusHours(random.nextInt(24)).plusMinutes(60)).meetingPlace("Callao, Madrid").build(),
                Plan.builder().name("3-Plan, horror + drinks").date(LocalDateTime.now().plusDays(1)).meetingPlace("Callao, Madrid").capacity(60).build(),
                Plan.builder().name("4-Plan drinks").date((LocalDateTime.now().plusDays(1))).meetingPlace("Callao, Madrid").build(),
                Plan.builder().name("5-Plan, night out, next weekend").date(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(23).withMinute(30).withSecond(0)).meetingPlace("Nuevos Ministerios, Madrid").build()
        };

        plans[0].addActivity(this.activityRepository.read(3L).get());
        plans[0].addActivity(this.activityRepository.read(5L).get());
        plans[0].addActivity(this.activityRepository.read(6L).get());

        plans[1].addActivity(this.activityRepository.read(1L).get());
        plans[1].addActivity(this.activityRepository.read(2L).get());

        plans[2].addActivity(this.activityRepository.read(3L).get());

        plans[3].addActivity(this.activityRepository.read(4L).get());
        plans[3].addActivity(this.activityRepository.read(5L).get());

        plans[4].addActivity(this.activityRepository.read(5L).get());

        plans[5].addActivity(this.activityRepository.read(7L).get());

        plans[0].addSubscriber(this.userRepository.read(1L).get());
        plans[1].addSubscriber(this.userRepository.read(1L).get());
        plans[2].addSubscriber(this.userRepository.read(1L).get());

        plans[0].addSubscriber(this.userRepository.read(2L).get());
        plans[1].addSubscriber(this.userRepository.read(2L).get());
        plans[2].addSubscriber(this.userRepository.read(2L).get());

        plans[0].addSubscriber(this.userRepository.read(3L).get());
        plans[1].addSubscriber(this.userRepository.read(3L).get());
        plans[2].addSubscriber(this.userRepository.read(3L).get());

        plans[0].addSubscriber(this.userRepository.read(4L).get());
        plans[1].addSubscriber(this.userRepository.read(4L).get());
        plans[2].addSubscriber(this.userRepository.read(4L).get());

        plans[0].addSubscriber(this.userRepository.read(5L).get());
        plans[1].addSubscriber(this.userRepository.read(5L).get());
        plans[2].addSubscriber(this.userRepository.read(5L).get());

        for (Plan plan : plans) {
            plan.setOwner(this.userRepository.read(1L).get());
            this.planRepository.create(plan);
        }
        LogManager.getLogger(this.getClass()).warn("        ------- plans");
    }

    public void deleteAll() {
        LogManager.getLogger(this.getClass()).warn("------- Spm Delete All ----------");
        this.activityRepository.deleteAll();
        this.planRepository.deleteAll();
    }
}
