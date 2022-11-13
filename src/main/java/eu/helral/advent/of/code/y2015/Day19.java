package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day19 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 19: Medicine for Rudolph ---
	 * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
	 *
	 * Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made
	 * medicine. Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
	 *
	 * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing
	 * any Red-Nosed Reindeer molecule you need. It works by starting with some input molecule and then doing a
	 * series of replacements, one per step, until it has the right molecule.
	 *
	 * However, the machine has to be calibrated before it can be used. Calibration involves determining the number
	 * of molecules that can be generated in one step from a given starting point.
	 *
	 * For example, imagine a simpler machine that supports only the following replacements:
	 *
	 * H => HO
	 * H => OH
	 * O => HH
	 * Given the replacements above and starting with HOH, the following molecules could be generated:
	 *
	 * HOOH (via H => HO on the first H).
	 * HOHO (via H => HO on the second H).
	 * OHOH (via H => OH on the first H).
	 * HOOH (via H => OH on the second H).
	 * HHHH (via O => HH).
	 * So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one
	 * replacement from HOH. Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements:
	 * six from H, and three from O).
	 *
	 * The machine replaces without regard for the surrounding characters. For example, given the string H2O, the
	 * transition H => OO would result in OO2O.
	 *
	 * Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for
	 * which you need to calibrate the machine. How many distinct molecules can be created after all the different
	 * ways you can do one replacement on the medicine molecule?
	 *
	 * </pre>
	 *
	 */
	public int part1() {
		MoleculeMachine moleculeMachine = new MoleculeMachine();
		moleculeMachine.init(getInput());
		return moleculeMachine.countVariants();
	}

	private static class MoleculeMachine {
		private final Map<String, List<String>> conversions = new HashMap<>();
		private final Map<String, List<String>> reverseConversions = new HashMap<>();
		private final Pattern inputParse = Pattern.compile("(\\w+) => (\\w+)");
		private String startingValue;
		private List<String> reverseKeys;

		public void init(Stream<String> input) {
			input.forEach(this::parse);
			reverseKeys = new ArrayList<>(reverseConversions.keySet());
			reverseKeys.sort(Comparator.comparing(String::length, Comparator.reverseOrder())
					.thenComparing(Comparator.naturalOrder()));
		}

		public int countVariants() {
			return generateVariants(startingValue).size();
		}

		public Set<String> generateVariants(String input) {
			return generateVariants(input, conversions.entrySet());
		}

		private Set<String> generateVariants(String input, Set<Entry<String, List<String>>> entrySet) {
			Set<String> variants = new HashSet<>();
			for (Entry<String, List<String>> entry : entrySet) {
				int startPoint = 0;
				while (input.indexOf(entry.getKey(), startPoint) >= 0) {
					int current = input.indexOf(entry.getKey(), startPoint);
					int continuePoint = current + entry.getKey().length();
					for (String replacement : entry.getValue()) {
						String newVal = input.substring(0, current) + replacement + input.substring(continuePoint);
						variants.add(newVal);
					}
					startPoint = current + 1;
				}
			}
			return variants;
		}

		private void parse(String line) {
			if (line.isEmpty()) {
				return;
			}
			Matcher matcher = inputParse.matcher(line);
			if (matcher.matches()) {
				conversions.computeIfAbsent(matcher.group(1), k -> new ArrayList<>()).add(matcher.group(2));
				reverseConversions.computeIfAbsent(matcher.group(2), k -> new ArrayList<>()).add(matcher.group(1));
				return;
			}
			startingValue = line;
		}

		private int reverseCalculate(String input) {
			List<Solution> steps = new ArrayList<>();
			steps.add(new Solution(input, 0));
			while (!steps.get(0).isSolved()) {
				if (steps.get(0).hasNext()) {
					steps.add(0, steps.get(0).next());
				} else {
					steps.remove(0);
				}
			}
			return steps.get(0).steps;
		}

		private class Solution implements Iterator<Solution> {
			private final String value;
			private final int steps;

			private int keyIndex;
			private int valueIndex;
			private int index;
			private int nextIndex;

			public Solution(String value, int steps) {
				this.value = value;
				this.steps = steps;
			}

			boolean isSolved() {
				return "e".equals(value);
			}

			@Override
			public boolean hasNext() {
				while (keyIndex < reverseKeys.size()) {
					String key = reverseKeys.get(keyIndex);
					if (value.indexOf(key, nextIndex) >= 0) {
						index = value.indexOf(key, index);
						nextIndex = index + 1;
						return true;
					}
					keyIndex++;
				}
				return false;
			}

			@Override
			public Solution next() {
				String replacement = reverseConversions.get(reverseKeys.get(keyIndex)).get(valueIndex);
				int continuePoint = index + reverseKeys.get(keyIndex).length();
				return new Solution(value.substring(0, index) + replacement + value.substring(continuePoint),
						steps + 1);
			}
		}
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * Now that the machine is calibrated, you're ready to begin molecule fabrication.
	 *
	 * Molecule fabrication always begins with just a single electron, e, and applying replacements one at a time,
	 * just like the ones during calibration.
	 *
	 * For example, suppose you have the following replacements:
	 *
	 * e => H
	 * e => O
	 * H => HO
	 * H => OH
	 * O => HH
	 * If you'd like to make HOH, you start with e, and then make the following replacements:
	 *
	 * e => O to get O
	 * O => HH to get HH
	 * H => OH (on the second H) to get HOH
	 * So, you could make HOH after 3 steps. Santa's favorite molecule, HOHOHO, can be made in 6 steps.
	 *
	 * How long will it take to make the medicine? Given the available replacements and the medicine molecule in your
	 * puzzle input, what is the fewest number of steps to go from e to the medicine molecule?
	 * </pre>
	 */
	public int part2() {
		MoleculeMachine moleculeMachine = new MoleculeMachine();
		moleculeMachine.init(getInput());
		return moleculeMachine.reverseCalculate(moleculeMachine.startingValue);
	}

}
