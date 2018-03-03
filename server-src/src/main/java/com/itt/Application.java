
package com.itt;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Application class.
 */

@Slf4j
@EnableMongoAuditing
@SpringBootApplication
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * Repository instance for DB Entity.
     *
     *
     * Main class.
     *
     * @param args Arguments
     */
    public static void main(final String[] args) {

        // Set profiles when needed.
        /*
         * AnnotationConfigApplicationContext context = new
         * AnnotationConfigApplicationContext();
         * context.getEnvironment().setActiveProfiles("production");
         * context.scan("com.itt"); context.refresh();
         */
        SpringApplication.run(Application.class, args);
    }

    /**
     * Run method executes repository operations.
     *
     * @param args Arguments
     * @throws Exception Exception raised by run
     */
    @Override
    public final void run(final String... args)
            throws Exception {

        AppConfig appConfig = AppConfig.getInstance();
        log.info("Environment Type:" + appConfig.getEnvType());
    }

    /**
     * The bean method that lists all the registered beans. uncomment Bean
     * annotation if you want to list the registered beans.
     *
     * @param ctx Spring Application Context
     * @return Instance of CommandLineRunner bean
     */
    // @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {

        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
