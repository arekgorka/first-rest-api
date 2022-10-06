package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("you can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url","https://arekgorka.github.io/");
        context.setVariable("button", "Visit Website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("goodbay_message","See you soon!");
        context.setVariable("preview_message","Add new card to trello");
        context.setVariable("company_name",companyConfig.getCompanyName());
        context.setVariable("company_mail",companyConfig.getCompanyMail());
        context.setVariable("company_phone",companyConfig.getCompanyPhone());
        context.setVariable("company_address",companyConfig.getCompanyAddress());
        context.setVariable("company_address_number",companyConfig.getCompanyAddressNumber());
        context.setVariable("show_button",false);
        context.setVariable("is_friend",true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
