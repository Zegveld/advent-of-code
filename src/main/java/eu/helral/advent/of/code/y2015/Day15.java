package eu.helral.advent.of.code.y2015;

import static java.util.function.Function.identity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day15 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 15: Science for Hungry People ---
	 * Today, you set out on the task of perfecting your milk-dunking cookie recipe. All you have to do is find the right balance of ingredients.
	 *
	 * Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a list of the remaining ingredients you could use to finish the
	 * recipe (your puzzle input) and their properties per teaspoon:
	 *
	 * capacity (how well it helps the cookie absorb milk)
	 * durability (how well it keeps the cookie intact when full of milk)
	 * flavor (how tasty it makes the cookie)
	 * texture (how it improves the feel of the cookie)
	 * calories (how many calories it adds to the cookie)
	 * You can only measure ingredients in whole-teaspoon amounts accurately, and you have to be accurate so you can reproduce your results in the future.
	 * The total score of a cookie can be found by adding up each of the properties (negative totals become 0) and then multiplying together everything except calories.
	 *
	 * For instance, suppose you have these two ingredients:
	 *
	 * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
	 * Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
	 * Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of cinnamon (because the amounts of each ingredient must add up to 100) would
	 * result in a cookie with the following properties:
	 *
	 * A capacity of 44*-1 + 56*2 = 68
	 * A durability of 44*-2 + 56*3 = 80
	 * A flavor of 44*6 + 56*-2 = 152
	 * A texture of 44*3 + 56*-1 = 76
	 * Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now) results in a total score of 62842880, which happens to be the best score
	 * possible given these ingredients. If any properties had produced a negative total, it would have instead become zero, causing the whole score to
	 * multiply to zero.
	 *
	 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make?
	 *
	 * </pre>
	 */

	public long part1() {
		Map<Ingredient, Integer> amountPerIngredient = getInput().map(this::toIngredient)
				.collect(Collectors.toMap(identity(), k -> 0));

		while (amountPerIngredient.values().stream().mapToInt(i -> i).sum() < 100) {
			for (Ingredient ingredient : amountPerIngredient.keySet()) {
				amountPerIngredient.merge(ingredient, 5, Integer::sum);
			}
		}

		Map<Ingredient, Integer> step5 = determineBestOption(amountPerIngredient, 5);
		Map<Ingredient, Integer> step3 = determineBestOption(step5, 3);
		Map<Ingredient, Integer> step1 = determineBestOption(step3, 1);

		return calculateTotal(step1);
	}

	private Map<Ingredient, Integer> determineBestOption(Map<Ingredient, Integer> amountPerIngredient, int stepSize) {
		Map<Ingredient, Integer> current = amountPerIngredient;
		List<Ingredient> ingredients = new ArrayList<>(current.keySet());
		boolean positiveChangeHappening = true;
		while (positiveChangeHappening) {
			positiveChangeHappening = false;
			for (int i = 0; i < ingredients.size(); i++) {
				for (int j = 0; j < ingredients.size(); j++) {
					long initialTotal = calculateTotal(current);
					HashMap<Ingredient, Integer> changed = new HashMap<>(current);
					if (i == j) {
						continue;
					}
					changed.merge(ingredients.get(i), stepSize, Integer::sum);
					changed.merge(ingredients.get(j), -1 * stepSize, Integer::sum);
					if (calculateTotal(changed) > initialTotal) {
						current = changed;
						positiveChangeHappening = true;
					}
				}
			}
		}
		return current;
	}

	private long calculateTotal(Map<Ingredient, Integer> amountPerIngredient) {
		long capacity = sum(Ingredient::capacity, amountPerIngredient);
		long durability = sum(Ingredient::durability, amountPerIngredient);
		long flavor = sum(Ingredient::flavor, amountPerIngredient);
		long texture = sum(Ingredient::texture, amountPerIngredient);
		return capacity * durability * flavor * texture;
	}

	private long sum(Function<Ingredient, Integer> property, Map<Ingredient, Integer> amountPerIngredient) {
		return amountPerIngredient.entrySet().stream().mapToLong(e -> property.apply(e.getKey()) * e.getValue()).sum();
	}

	public long part2() {
		Map<Ingredient, Integer> amountPerIngredient = getInput().map(this::toIngredient)
				.collect(Collectors.toMap(identity(), k -> 0));

		while (amountPerIngredient.values().stream().mapToInt(i -> i).sum() < 100) {
			for (Ingredient ingredient : amountPerIngredient.keySet()) {
				amountPerIngredient.merge(ingredient, 5, Integer::sum);
			}
		}

		Map<Ingredient, Integer> result = determineBestOption500Calories(amountPerIngredient);

		return calculateTotal(result);
	}

	private Map<Ingredient, Integer> determineBestOption500Calories(Map<Ingredient, Integer> amountPerIngredient) {
		Map<Ingredient, Integer> current = amountPerIngredient;
		Map<String, Ingredient> ingredients = current.keySet().stream()
				.collect(Collectors.toMap(Ingredient::name, identity()));
		boolean positiveChangeHappening = true;
		while (positiveChangeHappening) {
			positiveChangeHappening = false;
			long initialTotal = calculateTotal(current);
			HashMap<Ingredient, Integer> changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), -3, Integer::sum);
			changed.merge(ingredients.get("Candy"), 1, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), 1, Integer::sum);
			changed.merge(ingredients.get("Sugar"), 1, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
			changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), 3, Integer::sum);
			changed.merge(ingredients.get("Candy"), -1, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), -1, Integer::sum);
			changed.merge(ingredients.get("Sugar"), -1, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
			changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), 5, Integer::sum);
			changed.merge(ingredients.get("Candy"), 0, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), -4, Integer::sum);
			changed.merge(ingredients.get("Sugar"), -1, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
			changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), -5, Integer::sum);
			changed.merge(ingredients.get("Candy"), 0, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), 4, Integer::sum);
			changed.merge(ingredients.get("Sugar"), 1, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
			changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), 2, Integer::sum);
			changed.merge(ingredients.get("Candy"), 1, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), -3, Integer::sum);
			changed.merge(ingredients.get("Sugar"), 0, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
			changed = new HashMap<>(current);
			changed.merge(ingredients.get("Frosting"), -2, Integer::sum);
			changed.merge(ingredients.get("Candy"), -1, Integer::sum);
			changed.merge(ingredients.get("Butterscotch"), 3, Integer::sum);
			changed.merge(ingredients.get("Sugar"), 0, Integer::sum);
			if (calculateTotal(changed) > initialTotal) {
				current = changed;
				positiveChangeHappening = true;
			}
		}
		return current;
		// 3 Frosting, -1 candy, -1 sugar, -1 butterscotch == 0 calories changed.
		// 2 Frosting, 1 candy, 0 sugar, -3 butterscotch == 0 calories changed.
		// 5 Frosting, 0 candy, -1 sugar, -4 butterscotch == 0 calories changed.
	}

	Pattern ingredientPattern = Pattern.compile(
			"(\\w+): capacity (\\-?\\d+), durability (\\-?\\d+), flavor (\\-?\\d+), texture (\\-?\\d+), calories (\\d+)");

	Ingredient toIngredient(String line) {
		Matcher matcher = ingredientPattern.matcher(line);
		matcher.matches();
		return new Ingredient(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
				Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)),
				Integer.parseInt(matcher.group(6)));
	}

	record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {

	}
}
