
package com.itt.mock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * This class provides access to application configuration as defined in,
 * application.properties. The configuration starting with "mock.rest" prefix
 * are accessed by this class.
 * 
 * @author Rakesh Sawan
 */
@Configuration("mockConfig")
@ConfigurationProperties(prefix = "mock.rest")
@Data
@Slf4j
public class MockServiceConfig {

    /**
     * Default constructor that is called when the Bean is initialized.
     */
    public MockServiceConfig() {
        log.info("Initalizing MockConfig bean");
    }
    /**
     * Determines if the Mock service should start or not.
     */
    private boolean enabled;
    /**
     * Port to be used by Mock Service.
     */
    private int servicePort;
    /**
     * Port to be used by Mock Proxy.
     */
    private int proxyPort;
    /**
     * Host where the Mock service and proxy would run.
     */
    private String host;

    /**
     * Get's the url of the Mock Service.
     * 
     * @return URL
     */
    public String getServiceUrl() {

        return "http://" + host + ":" + servicePort;
    }

    /**
     * Creates a @MockRestService bean.
     * @param mockServiceConfig Instance of @MockServiceConfig
     * @return Instance of @MockRestService
     */
    @Bean (name = "mockRestService")
    public MockRestService getRestService(final MockServiceConfig mockServiceConfig) {

        return MockRestService.getInstance(mockServiceConfig);

    }
}
