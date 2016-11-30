package net.lucianolattes.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Ensure using the <tt>springSecurityFilterChain</tt> before any other
 * potentially registered Filter.
 *
 * @author lucianolattes
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
