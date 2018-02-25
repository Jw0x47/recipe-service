package io.jwg.repl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import io.jwg.models.IngredientEgg;
import io.jwg.models.Measurement;
import io.jwg.models.RecipeEgg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class DataEntryRepl {

	private static final Logger LOG = LoggerFactory.getLogger(DataEntryRepl.class);
	private static Console CONSOLE = System.console();

	public static void main(String[] args) throws JsonProcessingException {
		CONSOLE = System.console();
		if (CONSOLE == null) {
			System.out.println("Unable to get reference to console");
			System.exit(1);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		while (true) {
			RecipeEgg egg = getRecipe();

			String json = objectMapper.writeValueAsString(egg);
			LOG.info("Created json: {}", json);
		}
	}

	private static RecipeEgg getRecipe() {
		String name = CONSOLE.readLine("Recipe Name: ");
		int calories = Integer.valueOf(CONSOLE.readLine("Recipe Calories: "));
		String link = CONSOLE.readLine("Recipe Link: ");
		boolean addIngredient = true;

		List<IngredientEgg> ingredients = new ArrayList<>();
		while (addIngredient) {
			ingredients.add(addIngredient());
			addIngredient = CONSOLE.readLine("Add another [y/n] ").substring(0, 1).equalsIgnoreCase("y");
		}
		return RecipeEgg.builder()
				.setName(name)
				.setLink(link)
				.setCalories(calories)
				.setIngredients(ingredients)
				.build();
	}

	private static IngredientEgg addIngredient() {
		String name = CONSOLE.readLine("Ingredient Name: ");
		double amount = Double.valueOf(CONSOLE.readLine("Ingredient Amount: "));
		String measuremntQ = String.format("Measurement (%s) ", ImmutableList.copyOf(Measurement.values()).toString());
		Measurement measurement = Measurement.fromString(CONSOLE.readLine(measuremntQ));
		return IngredientEgg.builder()
				.setName(name)
				.setAmount(amount)
				.setMeasurement(measurement)
				.build();
	}


}
