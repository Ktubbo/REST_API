package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCreatorService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("is_friend", false);
        context.setVariable("show_button", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTasksEmail(String message) {

        List<String> tasksList = taskRepository.findAll().stream().map(Task::toString).collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("admin_config",adminConfig);
        context.setVariable("tasks_url", "http://localhost:8080/v1/task/getTasks");
        context.setVariable("button", "Your Tasks");
        context.setVariable("show_button", true);
        context.setVariable("tasks_list", tasksList);
        return templateEngine.process("mail/daily_mail",context);
    }

    public String buildEmail(String message, String template) {
        String templateMessage="";
        switch (template) {
            case "TrelloCard":
                return templateMessage = buildTrelloCardEmail(message);
            case "Tasks":
                return templateMessage = buildTasksEmail(message);
        }
        return templateMessage;
    }

}

