package works.buddy.library.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "works.buddy.library")
@Import({HibernateConfig.class})
public class LibraryConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "hibernate.properties" ) };
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(resources);
        configurer.setNullValue("@null");
        configurer.setFileEncoding("UTF-8");
        configurer.setOrder(Ordered.LOWEST_PRECEDENCE);
        return configurer;
    }
}
