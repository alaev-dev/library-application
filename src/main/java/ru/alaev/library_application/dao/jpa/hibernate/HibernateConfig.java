package ru.alaev.library_application.dao.jpa.hibernate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "use.dataProvider", havingValue = "hibernate")
@Configuration
public class HibernateConfig {

}
