package net.lucianolattes.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.lucianolattes.security.SecurityConfig;

/**
 * This class acts as an application bootstrapper, registering an
 * annotation-based <tt>DispatcherServlet</tt>.
 *
 * @author lucianolattes
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { AppConfig.class, SecurityConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
