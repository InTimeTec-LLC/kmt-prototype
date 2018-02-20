
package com.itt.mock;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import javax.servlet.http.HttpServletResponse;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.action.ExpectationCallback;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for starting Mock server on the ports configured as
 * defined in application.properties. Also provides helper methods to configure
 * the server remotely.
 * 
 * @author Rakesh Sawan
 */
@Slf4j
public final class MockRestService {

    /**
     * MockRESTService Singleton Instance.
     */
    private static MockRestService instance = null;
    /**
     * Mock Server Proxy instance.
     */
    private ClientAndProxy proxy;
    /**
     * Mock Server Service instance.
     */
    private ClientAndServer mockServer;

    /**
     * @MockServiceConfig instance.
     */
    private MockServiceConfig mockConfig;

    /**
     * Default constructor that is used for the Bean Initialization by the
     * Spring framework.
     * 
     * @param mockConfig instance of the @MockServiceConfig Bean
     */
    // @Autowired
    private MockRestService(final MockServiceConfig mockConfig) {
        log.info("Initializing MockRestService Bean");
        this.mockConfig = mockConfig;
        start();
    }

    /**
     * Returns a singleton instance of MockRestService.
     * 
     * @param mockConfig @MockServiceConfig instance
     * @return @MockRestService instance
     */
    @Autowired
    public static synchronized MockRestService getInstance(final MockServiceConfig mockConfig) {

        if (instance == null) {
            instance = new MockRestService(mockConfig);
        }
        return instance;
    }

    /**
     * Starts the mock service and proxy.
     */
    public void start() {

        if (!mockConfig.isEnabled()) {
            log.info("Mock Configuration disabled, not starting mock service");
            return;
        }

        if (mockServer == null) {
            mockServer = ClientAndServer.startClientAndServer(mockConfig.getServicePort());
            proxy = ClientAndProxy.startClientAndProxy(mockConfig.getProxyPort());
        }
    }

    /**
     * Stops the mock service and proxy.
     */
    public void stop() {

        if (!mockConfig.isEnabled()) {
            log.info("Mock Service is disabled");
            return;
        }
        proxy.stop();
        mockServer.stop();
        mockServer = null;
    }

    /**
     * Helper method to setup a mock endpoint and response.
     * 
     * @param httpMethod HTTP method that needs to be mocked such as POST, GET,
     *            PUT etc.
     * @param endpoint The endpoint that needs to be mocked, example /books.
     * @param responseFileName Name of the file that needs to be sent as a
     *            response, the file should be placed in resources folder
     */
    public void setupMock(final String httpMethod, final String endpoint, final String responseFileName) {

        if (!mockConfig.isEnabled()) {
            log.info("Mock Service is disabled");
            return;
        }

        new MockServerClient(mockConfig.getHost(), mockConfig.getServicePort()).when(
            request().withMethod(httpMethod).withPath(endpoint)).respond(
                response().withStatusCode(HttpServletResponse.SC_OK).withHeader(
                    "content-type", "application/json;charset=UTF-8").withBody(
                        ResourceFileLoader.loadResource(responseFileName)));

    }

    /**
     * Helper method to setup a mock endpoint and response.
     * 
     * @param httpMethod HTTP method that needs to be mocked such as POST, GET,
     *            PUT etc.
     * @param endpoint The endpoint that needs to be mocked, example /books.
     * @param expectationCallback Callback that will be invoked when the request
     *            is matched. The callback will be responsible for sending the
     *            response.
     */
    public void setupMock(
        final String httpMethod, final String endpoint, final ExpectationCallback expectationCallback) {

        if (!mockConfig.isEnabled()) {
            log.info("Mock Service is disabled");
            return;
        }

        new MockServerClient(mockConfig.getHost(), mockConfig.getServicePort()).when(
            request().withMethod(httpMethod).withPath(endpoint)).callback(expectationCallback);

    }
}
