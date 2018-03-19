package io.jwg;

import com.google.common.collect.Iterators;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.*;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;

/**
 * Allows Cors headers
 * Modeled on the HubSpot/Singularity filter
 */
public class CorsBundle implements ConfiguredBundle<RecipeConfiguration> {

	private static final String FILTER_NAME = "Cross Origin Request Filter";

	@Override
	public void initialize(final Bootstrap<?> bootstrap) {
	}

	@Override
	public void run(final RecipeConfiguration config, final Environment environment) {

		final Filter corsFilter = new CrossOriginFilter();
		final FilterConfig corsFilterConfig =
				new FilterConfig() {

					@Override
					public String getFilterName() {
						return FILTER_NAME;
					}

					@Override
					public ServletContext getServletContext() {
						return null;
					}

					@Override
					public String getInitParameter(final String name) {
						return null;
					}

					@Override
					public Enumeration<String> getInitParameterNames() {
						return Iterators.asEnumeration(Collections.<String>emptyIterator());
					}
				};

		try {
			corsFilter.init(corsFilterConfig);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		FilterRegistration.Dynamic filter = environment.servlets().addFilter(FILTER_NAME, corsFilter);

		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
		filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
	}
}
