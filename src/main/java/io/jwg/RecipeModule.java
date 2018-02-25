package io.jwg;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Binder;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import io.dropwizard.db.DataSourceFactory;
import io.jwg.db.RecipeDataModule;
import io.jwg.resources.RecipeResource;

public class RecipeModule extends DropwizardAwareModule<RecipeConfiguration> {

	@Override
	public void configure(Binder binder) {
		binder.install(new RecipeDataModule());
		binder.bind(RecipeResource.class);
		binder.bind(MetricRegistry.class).toInstance(getEnvironment().metrics());
		binder.bind(DataSourceFactory.class).toInstance(getConfiguration().getDatabaseConfiguration());

	}
}
