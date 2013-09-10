package lt.brick1;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartBrick1Jetty {
  public static void main(String[] args) throws Exception {
    startJettyServer(9080, "/");
  }

  private static void startJettyServer(int port, String contextPath) throws Exception {
    int timeout = 60 * (60 * 1000);

    Server server = new Server();
    SocketConnector connector = new SocketConnector();

    // Set some timeout options to make debugging easier.
    connector.setMaxIdleTime(timeout);
    connector.setSoLingerTime(-1);
    connector.setPort(port);
    server.addConnector(connector);

    WebAppContext webAppContext = new WebAppContext();
    webAppContext.setServer(server);
    webAppContext.setContextPath(contextPath);
    webAppContext.setWar("src/main/webapp");

    // START JMX SERVER
    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
    server.getContainer().addEventListener(mBeanContainer);
    mBeanContainer.start();

    server.setHandler(webAppContext);

    System.out.println("-------------- starting Jetty ...");
    server.start();
    System.out.println("-------------- starting Jetty ... done:  http://localhost:" + port);
    System.in.read();
    System.out.println("-------------- stopping Jetty ...");
    server.stop();
    server.join();
    System.out.println("-------------- stopping Jetty ... done");
  }
}
