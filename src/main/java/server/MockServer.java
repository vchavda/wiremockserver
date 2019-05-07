package server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class MockServer {
    private final Logger logger = LoggerFactory.getLogger(MockServer.class);

    private static final String CONTENT_TYPE_VALUE = "application/json";

    private  WireMockServer wireMockServer = null;
    private  WireMockConfiguration wireMockConfiguration = null;
    private int port = 0;

    public MockServer() throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        port = Integer.parseInt(props.getProperty("port"));
        wireMockConfiguration = new WireMockConfiguration().port(port);
        wireMockServer = new WireMockServer(wireMockConfiguration);
    }

    public void start() {
        logger.info("Starting Mock Server on port {}", port);
        wireMockServer.start();
        reset();
        loadSampleResponseJSON();
    }

    private void loadSampleResponseJSON() {
            basicGet("/myapirequest", "__files/sample.json");
    }

    private void basicGet(String url, String bodyContent) {
        stubFor(WireMock.get(urlMatching(url))
                .willReturn(aResponse()
                .withHeader("Content-Type", CONTENT_TYPE_VALUE)
                .withBody(loadResponseFromFile(bodyContent))));

    }

    private String loadResponseFromFile(String classpathLocation) {
        logger.info("loading stub response from file: {}", classpathLocation);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(classpathLocation);
        System.out.println("XXX " + resourceAsStream);
        return (new Scanner(resourceAsStream,"utf-8")).useDelimiter("\\Z").next();
    }


    private void reset() {
        wireMockServer.resetAll();
        WireMock.configureFor(port);
    }


}
