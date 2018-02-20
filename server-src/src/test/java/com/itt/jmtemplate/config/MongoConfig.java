
package com.itt.jmtemplate.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

//Override the default config, not used as of now, uncomment @Configuration tag to start using.
//@Configuration
public class MongoConfig {

    private static MongoTemplate mongoTemplate = null;
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "javaTemplate";

    @Bean
    public MongoTemplate mongoTemplate()
        throws IOException {

        if (mongoTemplate != null) {
            return mongoTemplate;
        }

        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        //mongo.setPort(12345);
        MongoClient mongoClient = mongo.getObject();
        mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);

        return mongoTemplate;
    }
}
