package io.jwg.models;

import org.immutables.value.Value;

import java.util.List;

@RecipeStyle
@Value.Immutable
public interface RecipeEggWithEntriesIF extends RecipeCore {

	List<RecipeEntry> getEntries();

}
