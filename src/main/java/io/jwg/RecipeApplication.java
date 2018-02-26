package io.jwg;

import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
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
		bootstrap.addBundle(createMigrationsBundle());
	}

	@Override
	public void run(RecipeConfiguration configuration, Environment environment) throws Exception {
	}

	private MigrationsBundle<RecipeConfiguration> createMigrationsBundle() {

		return new MigrationsBundle<RecipeConfiguration>() {

			@Override
			public String getMigrationsFileName() {
				return "schema.sql";
			}

			@Override
			public DataSourceFactory getDataSourceFactory(final RecipeConfiguration configuration) {
				return configuration.getDatabaseConfiguration();
			}
		};
	}

}
