package lt.brick1;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.SitebricksServletModule;
import lt.brick1.util.LoggerOfHttpSessionSize;

public class AppStartupListener extends GuiceServletContextListener {
  @Override public Injector getInjector() {
    return Guice.createInjector(new SitebricksModule() {
      @Override protected void configureSitebricks() {
        at("/").show(HomePage.class);
      }

      @Override protected SitebricksServletModule servletModule() {
        return new SitebricksServletModule() {
          @Override protected void configurePreFilters() {
            filter("/*").through(new LoggerOfHttpSessionSize());
          }
        };
      }
    });
  }
}
