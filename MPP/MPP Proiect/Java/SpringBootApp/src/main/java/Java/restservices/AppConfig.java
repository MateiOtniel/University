package Java.restservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.repository.dbrepositories.TestRepository;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public Properties myProperties() {
        Properties properties = new Properties();
        properties.setProperty("server.port", "55556");
        properties.setProperty("jdbc.driver", "org.sqlite.JDBC");
        properties.setProperty("jdbc.url", "jdbc:sqlite:Triathlon");
        return properties;
    }

    @Bean
    public TestRepository testRepository() {
        return new TestRepository(myProperties());
    }
}
