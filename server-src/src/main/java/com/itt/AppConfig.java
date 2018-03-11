
package com.itt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.itt.utility.ArticleFilterConverter;

/**
 * This class provides access to application configuration as defined in,
 * application.properties.
 * 
 * @author Rakesh Sawan
 */
@Configuration("config")
@EnableWebMvc
@EnableMongoAuditing
public class AppConfig extends WebMvcConfigurationSupport {

    /**
     * configuration value that corresponds to DEV.
     */
    public static final String DEV = "dev";
    /**
     * configuration value that corresponds to PROD.
     */
    public static final String PROD = "prod";

    /**
     * Gets the instance of AppConfig bean. Ideally @Autowired annotation should
     * work, but for some reason it's not working hence we are creating the
     * instance ourselves.
     * 
     * @return AppConfig instance
     */
    public static AppConfig getInstance() {

        ApplicationContext applicationContext = ApplicationContextProvider.getContext();
        return (AppConfig) applicationContext.getBean(AppConfig.class);
    }
    /**
     * The environment type such as DEV,PROD etc.
     */
    @Value("${env_type}")
    private String envType;

    /**
     * Gets the environment type.
     * 
     * @return String "dev", "prod" etc
     */
    public String getEnvType() {

        return envType;
    }

    /**
     * Determines if the current environment is DEV.
     * 
     * @return true if running in dev environment.
     */
    public boolean isDevEnv() {

        return envType.equals(DEV);
    }

    /**
     * include a converter to set null when other than specified enum value is entered.
     * 
     * @return formattingConversionService , returns the object of FormattingConversionService.
     */
    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService formattingConversionService = super.mvcConversionService();
        formattingConversionService.addConverter(new ArticleFilterConverter());
        return formattingConversionService;
    }
}
