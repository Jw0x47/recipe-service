package io.jwg.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.immutables.value.Value;

public interface RecipeComponentCoreIF {

	String getName();

	@Value.Check
	default void checkWhitespace() {
		Preconditions.checkArgument(getName().equalsIgnoreCase(getName().trim()), "No whitespace in component names");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(getName()), "No empty component names");
	}
}
