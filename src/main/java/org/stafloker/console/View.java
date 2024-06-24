package org.stafloker.console;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public interface View {
    /**
     * @return [0] ~ comando , [i>=1] - parametros (si hay)
     */
    String[] enterCommand();

    void showWelcome();

    void showCommand(String message);

    void showHelp(Map<String, List<String[]>> commandCategories, List<String[]> generalCommands);

    void showErrorWithComment(String errorMessage, String comment);

    void showResult(String message);

    void show(String message);

    void showUser(Long id, String username, String password, Integer age, String mobile);

    void showPlan(Long id, String name, String ownerName, LocalDateTime date, String meetingPlace, Integer capacity, Integer availableSpots, List<String> activitiesNames, List<String> subscribersNames);

    void showPlanForList(Long id, String name, String ownerName, LocalDateTime date, String meetingPlace, Integer capacity, Integer availableSpots, List<String> activitiesNames, List<String> subscribersNames);

    void showActivity(Long id, String name, String description, String type, Integer duration, Integer capacity, Double price);
}