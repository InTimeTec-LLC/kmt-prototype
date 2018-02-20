
package com.itt.jmtemplate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;

@Configuration
public class TestApplicationConfig extends AbstractMongoConfiguration {

    private static Mongo mongo = null;
    private static MongoTemplate mongoTemplate = null;
    private final int mongoPort = 12345;
    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {

        return "unitTestDatabase";
    }

    /**
     * Ensures that only one instance of embeddded mongodb is started for one
     * test run.
     */
    @Override
    public synchronized Mongo mongo()
        throws Exception {

        if (mongo == null) {
            mongo = new EmbeddedMongoBuilder().version("2.6.1").bindIp("127.0.0.1").port(mongoPort).build();
        }
        return mongo;
    }
}
