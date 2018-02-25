package io.jwg.db;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.skife.jdbi.v2.DBI;


public class DaoProvider<T> implements Provider<T> {
	private final Class<T> type;
	private Optional<DBI> dbi;

	public DaoProvider(Class<T> type) {
		this.type = type;
		this.dbi = Optional.absent();
	}

	@Inject
	public void setDBI(DBI dbi) {
		this.dbi = Optional.of(dbi);
	}

	@Override
	public T get() {
		return dbi.get().onDemand(type);
	}
}

