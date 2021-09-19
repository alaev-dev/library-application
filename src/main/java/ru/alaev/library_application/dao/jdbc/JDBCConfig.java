package ru.alaev.library_application.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "use.dataProvider", havingValue = "jdbc")
@Configuration
public class JDBCConfig {

}
