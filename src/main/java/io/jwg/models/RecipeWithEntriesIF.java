package io.jwg.models;

import org.immutables.value.Value;

import java.util.List;

@RecipeStyle
@Value.Immutable
public interface RecipeWithEntriesIF extends RecipeCore {

	int getId();

	List<RecipeEntry> getEntries();

}
