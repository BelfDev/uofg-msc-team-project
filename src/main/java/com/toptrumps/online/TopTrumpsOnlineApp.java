package com.toptrumps.online;

import com.toptrumps.online.api.TopTrumpsRESTAPI;
import com.toptrumps.online.configuration.TopTrumpsJSONConfiguration;
import com.toptrumps.online.resources.GameWebPagesResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Top Trumps Web Application. This class is complete, you do not need to edit it, you
 * instead need to complete TopTrumpsRESTAPI and the HTML/Javascript views.
 */
public class TopTrumpsOnlineApp extends Application<TopTrumpsJSONConfiguration> {

    private static String ONLINE_RESOURCES = "/com/toptrumps/online";

    /**
     * This is the main class for the Top Trumps Web application. It is called by TopTrumps.java
     * when the user specifies that they want to run in online mode.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new TopTrumpsOnlineApp().run(args); // Create a new online application and run it
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    /**
     * This is the Dropwizard run method after argument parsing has happened
     */
    public void run(TopTrumpsJSONConfiguration conf, Environment environment)
            throws Exception {

        // Enable CORS headers (see https://en.wikipedia.org/wiki/Cross-origin_resource_sharing)
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Dropwizard expresses things that the user can ask for as resources. We have two of
        // these, the REST api and the HTML/Javascript Webpages

        // REST API
        TopTrumpsRESTAPI restAPI = new TopTrumpsRESTAPI(conf);

        // HTML/Javascript Webpages
        GameWebPagesResource gameScreen = new GameWebPagesResource();

        // Registration tells Dropwizard to host a resource
        environment.jersey().register(restAPI);
        environment.jersey().register(gameScreen);
    }


    /**
     * Get the name of the application
     */
    @Override
    public String getName() {
        return "TopTrumps";
    }


    /**
     * An initalization method that attaches the Configuration to the views
     */
    @Override
    public void initialize(Bootstrap<TopTrumpsJSONConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<TopTrumpsJSONConfiguration>());
        bootstrap.addBundle(new AssetsBundle(ONLINE_RESOURCES + "/scripts", "/assets/scripts/",  null, "scripts"));
        bootstrap.addBundle(new AssetsBundle(ONLINE_RESOURCES + "/styles", "/assets/styles/",  null, "styles"));
        bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/assets/fonts/",  null, "fonts"));
        bootstrap.addBundle(new AssetsBundle("/assets/images", "/assets/images/",  null, "images"));
    }
}
