package io.jwg.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.immutables.value.Value;

public interface RecipeCore {

	String getName();

	String getLink();

	@Value.Check
	default void checkWhitespace() {
		Preconditions.checkArgument(getName().equalsIgnoreCase(getName().trim()), "No whitespace in recipe names");
		Preconditions.checkArgument(getLink().equalsIgnoreCase(getLink().trim()), "No whitespace in links");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(getName()), "No empty recipe names");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(getLink()), "No empty links");
	}
}
