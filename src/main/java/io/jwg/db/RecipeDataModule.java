package io.jwg.db;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.*;
import com.hubspot.guice.transactional.DataSourceLocator;
import com.hubspot.guice.transactional.TransactionalDataSource;
import com.hubspot.guice.transactional.TransactionalModule;
import com.hubspot.guice.transactional.impl.DefaultDataSourceLocator;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import org.skife.jdbi.v2.DBI;

public class RecipeDataModule extends AbstractModule {

	private static <T> void bindDao(Binder binder, Class<T> type) {
		binder.bind(type).toProvider(new DaoProvider<>(type)).in(Scopes.SINGLETON);
	}

	@Override
	protected void configure() {
		install(new TransactionalModule());

		bind(DataSourceLocator.class).to(DefaultDataSourceLocator.class).in(Scopes.SINGLETON);
		bind(DBI.class).toProvider(DBIProvider.class).in(Scopes.SINGLETON);

		bindDao(binder(), RecipeDao.class);
		bindDao(binder(), IngredientDao.class);
	}

	@Provides
	@Singleton
	public ManagedDataSource providesManagedDataSource(DataSourceFactory dataSourceFactory,
																										 MetricRegistry metricRegistry) throws ClassNotFoundException {
		return dataSourceFactory.build(metricRegistry, "db");
	}

	@Provides
	@Singleton
	public TransactionalDataSource providesTransactionalDataSource(ManagedDataSource managedDataSource) {
		return new TransactionalDataSource(managedDataSource);
	}

	@Override
	public boolean equals(Object o) {
		return o != null && getClass().equals(o.getClass());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}

