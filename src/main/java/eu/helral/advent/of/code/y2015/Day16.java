package eu.helral.advent.of.code.y2015;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day16 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 16: Aunt Sue ---
	 * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card. However, there's a small problem: she signed it "From, Aunt Sue".
	 *
	 * You have 500 Aunts named "Sue".
	 *
	 * So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue (which you conveniently number 1 to 500, for sanity) gave you the gift. You open the present and, as luck would have it, good ol' Aunt Sue got you a My First Crime Scene Analysis Machine! Just what you wanted. Or needed, as the case may be.
	 *
	 * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample, as well as how many distinct kinds of those compounds there are. According to the instructions, these are what the MFCSAM can detect:
	 *
	 * children, by human DNA age analysis.
	 * cats. It doesn't differentiate individual breeds.
	 * Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
	 * goldfish. No other kinds of fish.
	 * trees, all in one group.
	 * cars, presumably by exhaust or gasoline or something.
	 * perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
	 * In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM. It beeps inquisitively at you a few times and then prints out a message on ticker tape:
	 *
	 * children: 3
	 * cats: 7
	 * samoyeds: 2
	 * pomeranians: 3
	 * akitas: 0
	 * vizslas: 0
	 * goldfish: 5
	 * trees: 3
	 * cars: 2
	 * perfumes: 1
	 * You make a list of the things you can remember about each Aunt Sue. Things missing from your list aren't zero - you simply don't remember the value.
	 *
	 * What is the number of the Sue that got you the gift?
	 * </pre>
	 */

	Pattern suePattern = Pattern.compile("Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");

	public int part1() {
		return getInput().map(this::toSue).filter(this::isWantedSuePart1).map(Sue::getNumber).findFirst().get();
	}

	/**
	 * <pre>
	 * As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye. Apparently,
	 * it has an outdated retroencabulator, and so the output from the machine isn't exact values - some of them indicate ranges.
	 *
	 * In particular, the cats and trees readings indicates that there are greater than that many (due to the unpredictable nuclear
	 * decay of cat dander and tree pollen), while the pomeranians and goldfish readings indicate that there are fewer than that many
	 * (due to the modial interaction of magnetoreluctance).
	 *
	 * What is the number of the real Aunt Sue?
	 * </pre>
	 */
	public Integer part2() {
		return getInput().map(this::toSue).filter(this::isWantedSuePart2).map(Sue::getNumber).findFirst().get();
	}

	private Sue toSue(String line) {
		Matcher matcher = suePattern.matcher(line);
		matcher.matches();
		return new Sue(Integer.valueOf(matcher.group(1)), matcher.group(2), Integer.valueOf(matcher.group(3)),
				matcher.group(4), Integer.valueOf(matcher.group(5)), matcher.group(6),
				Integer.valueOf(matcher.group(7)));
	}

	private static class Sue {
		private final Integer number;
		private final Map<String, Integer> details = new HashMap<>();

		public Sue(Integer number, String item1, Integer value1, String item2, Integer value2, String item3,
				Integer value3) {
			this.number = number;
			details.put(item1, value1);
			details.put(item2, value2);
			details.put(item3, value3);
		}

		public Integer getNumber() {
			return number;
		}

		public boolean matches(Map<String, Integer> wantedMap) {
			for (Entry<String, Integer> detail : details.entrySet()) {
				if (wantedMap.get(detail.getKey()) != detail.getValue()) {
					return false;
				}
			}
			return true;
		}

		public boolean matchesPredicate(Map<String, Predicate<Integer>> wantedMap) {
			for (Entry<String, Integer> detail : details.entrySet()) {
				if (!wantedMap.get(detail.getKey()).test(detail.getValue())) {
					return false;
				}
			}
			return true;
		}
	}

	private boolean isWantedSuePart1(Sue sue) {
		Map<String, Integer> wantedMap = Map.of("children", 3, "cats", 7, "samoyeds", 2, "pomeranians", 3, "akitas", 0,
				"vizslas", 0, "goldfish", 5, "trees", 3, "cars", 2, "perfumes", 1);
		return sue.matches(wantedMap);
	}

	private boolean isWantedSuePart2(Sue sue) {
		Map<String, Predicate<Integer>> wantedMap = Map.of("children", (Predicate<Integer>) i -> i == 3, "cats",
				(Predicate<Integer>) i -> i > 7, "samoyeds", (Predicate<Integer>) i -> i == 2, "pomeranians",
				(Predicate<Integer>) i -> i < 3, "akitas", (Predicate<Integer>) i -> i == 0, "vizslas",
				(Predicate<Integer>) i -> i == 0, "goldfish", (Predicate<Integer>) i -> i < 5, "trees",
				(Predicate<Integer>) i -> i > 3, "cars", (Predicate<Integer>) i -> i == 2, "perfumes",
				(Predicate<Integer>) i -> i == 1);
		return sue.matchesPredicate(wantedMap);
	}
}
