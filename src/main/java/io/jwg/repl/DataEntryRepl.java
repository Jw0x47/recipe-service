package io.jwg.repl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.hubspot.horizon.HttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.ApacheHttpClient;
import io.jwg.models.Measurement;
import io.jwg.models.RecipeEggWithEntries;
import io.jwg.models.RecipeEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DataEntryRepl {

	private static final Logger LOG = LoggerFactory.getLogger(DataEntryRepl.class);
	private static Console CONSOLE = System.console();
	private static final HttpClient HTTP_CLIENT = new ApacheHttpClient();


	// Monday Garlic knots && Eggplant Pasta
// Lefover knots && ??
// Meatballs
// TBD
// TBD
	public static void main(String[] args) throws JsonProcessingException {
		CONSOLE = System.console();
		if (CONSOLE == null) {
			System.out.println("Unable to get reference to console");
			System.exit(1);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		while (true) {
			RecipeEggWithEntries egg = getRecipe();

			String json = objectMapper.writeValueAsString(egg);
			LOG.debug("Created json: {}", json);
			addRecipe(egg);
		}
	}

	private static void addRecipe(RecipeEggWithEntries egg) {
		HttpRequest request = HttpRequest.newBuilder()
				.setMethod(HttpRequest.Method.POST)
				.setUrl("http://localhost:8080/recipes/add")
				.setBody(egg)
				.build();

		HttpResponse response = HTTP_CLIENT.execute(request);
		if (response.getStatusCode() != 204) {
			LOG.error(response.getAsString());
			return;
		}
		LOG.info("Added recipe to database");
	}

	private static RecipeEggWithEntries getRecipe() {
		String name = CONSOLE.readLine("Recipe Name: ");
		String link = CONSOLE.readLine("Recipe Link: ");
		boolean addIngredient = true;

		List<RecipeEntry> entries = new ArrayList<>();
		while (addIngredient) {
			entries.add(tryUntil(DataEntryRepl::addRecipeEntry, 2));
			addIngredient = CONSOLE.readLine("Add another [y/n] ").substring(0, 1).equalsIgnoreCase("y");
		}
		return RecipeEggWithEntries.builder()
				.setName(name)
				.setLink(link)
				.setEntries(entries)
				.build();
	}

	private static RecipeEntry addRecipeEntry() {
		String name = CONSOLE.readLine("Ingredient Name: ");
		double amount = Double.valueOf(CONSOLE.readLine("Ingredient Amount: "));
		String measurementQuestion = String.format("Measurement (%s) ", ImmutableList.copyOf(Measurement.values()).toString());
		String measurementStr = CONSOLE.readLine(measurementQuestion);
		Measurement measurement = Measurement.fromString(measurementStr);
		return RecipeEntry.builder()
				.setName(name)
				.setAmount(amount)
				.setMeasurement(measurement)
				.build();
	}


	private static <T> T tryUntil(Supplier<T> supplier, int tries) {
		int counter = tries;
		while (counter >= 0) {
			try {
				return supplier.get();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Lets try again!");
				counter--;
			}
		}
		throw new RuntimeException("Failed to get data after " + tries + "tries.");
	}

}
