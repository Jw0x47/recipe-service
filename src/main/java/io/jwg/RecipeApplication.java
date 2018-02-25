package io.jwg;

import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class RecipeApplication extends Application<RecipeConfiguration> {

	public static void main(String... args) throws Exception {
		new RecipeApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<RecipeConfiguration> bootstrap) {
		GuiceBundle<RecipeConfiguration> guiceBundle = GuiceBundle.defaultBuilder(RecipeConfiguration.class)
				.enableGuiceEnforcer(false)
				.modules(new RecipeModule())
				.build();

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public void run(RecipeConfiguration configuration, Environment environment) throws Exception {
	}
}
