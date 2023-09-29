import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.predicate.PredicatesHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.builder.PredicatedHandlersParser;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.HttpString;
import jakarta.servlet.ServletContextListener;
import org.abf.hobt.servlet.HobtServletContextListener;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Embedded (Undertow) server for development. Uses the settings from <code>web.xml</code>
 *
 * @author Hector Plahar
 */
public class DevelopmentServer {

    public static void main(String[] args) throws Exception {
        DeploymentInfo servletBuilder = Servlets.deployment()
            .setClassLoader(ServletContextListener.class.getClassLoader())
            .addListener(Servlets.listener(HobtServletContextListener.class))
            .setContextPath("/")
            .setDeploymentName("Host OnBoarding Tool")
//            .setResourceManager(new FileResourceManager(new File("src/main/webapp"), 10)).addWelcomePage("index.htm")
            .addServlets(
                Servlets.servlet("Jersey REST Servlet", ServletContainer.class)
                    .addInitParam("jersey.config.server.provider.packages", "org.abf.hobt.service.rest")
                    .addInitParam("jersey.config.server.provider.scanning.recursive", "false")
                    .addInitParam("jakarta.ws.rs.core.Application", "org.abf.hobt.HobtApplication")
                    .setAsyncSupported(true)
                    .setEnabled(true)
                    .addMapping("/rest/*")
            );

        // deploy servlet
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        HttpHandler servletHandler = manager.start();

        PredicatesHandler handler = Handlers.predicates(PredicatedHandlersParser.parse(
            "path-prefix('hosts') or path-prefix('login') or path-prefix('admin') and regex('/.+') -> rewrite('/')",
            ClassLoader.getSystemClassLoader()), servletHandler);

        PathHandler path = Handlers.path(Handlers.redirect("/"))
            .addPrefixPath("/", handler);

        Undertow server = Undertow.builder()
            .addHttpListener(8082, "localhost")
            .setHandler(exchange -> {
                exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
                exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"), "GET,PUT,POST,DELETE,OPTIONS");
                exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Headers"), "Content-Type, X-HOBT-Authentication-SessionId");

                path.handleRequest(exchange);
            })
            .build();
        server.start();
    }
}
