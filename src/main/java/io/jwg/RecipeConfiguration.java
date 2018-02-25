package io.jwg;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RecipeConfiguration extends Configuration {
    // TODO: implement service configuration
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory databaseConfiguration;

    public DataSourceFactory getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    public RecipeConfiguration setDatabaseConfiguration(DataSourceFactory databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        return this;
    }
}
