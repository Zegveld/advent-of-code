package eu.helral.advent.of.code.y2015;

import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day13 extends DayTemplate {

	/**
	 * https://adventofcode.com/2015/day/13
	 *
	 * <pre>
	 * --- Day 13: Knights of the Dinner Table ---
	 * In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you resolve, will be different. You're going to find the optimal seating arrangement and avoid all those awkward conversations.
	 *
	 * You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they were to find themselves sitting next to each other person. You have a circular table that will be just big enough to fit everyone comfortably, and so each person will have exactly two neighbors.
	 *
	 * For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:
	 *
	 * Alice would gain 54 happiness units by sitting next to Bob.
	 * Alice would lose 79 happiness units by sitting next to Carol.
	 * Alice would lose 2 happiness units by sitting next to David.
	 * Bob would gain 83 happiness units by sitting next to Alice.
	 * Bob would lose 7 happiness units by sitting next to Carol.
	 * Bob would lose 63 happiness units by sitting next to David.
	 * Carol would lose 62 happiness units by sitting next to Alice.
	 * Carol would gain 60 happiness units by sitting next to Bob.
	 * Carol would gain 55 happiness units by sitting next to David.
	 * David would gain 46 happiness units by sitting next to Alice.
	 * David would lose 7 happiness units by sitting next to Bob.
	 * David would gain 41 happiness units by sitting next to Carol.
	 * Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), but David would gain 46 happiness units (because Alice is such a good listener), for a total change of 44.
	 *
	 * If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54). Finally, seat Carol, who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). The arrangement looks like this:
	 *
	 *      +41 +46
	 * +55   David    -2
	 * Carol       Alice
	 * +60    Bob    +54
	 *      -7  +83
	 * After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, with a total change in happiness of 330.
	 *
	 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
	 *
	 * </pre>
	 */
	public int part1() {
		HappinessCalculator hapinessCalculator = new HappinessCalculator(true);
		getInput().forEach(hapinessCalculator::parseAndAdd);
		return hapinessCalculator.getMaximum();
	}

	public int part2() {
		HappinessCalculator hapinessCalculator = new HappinessCalculator(false);
		getInput().forEach(hapinessCalculator::parseAndAdd);
		return hapinessCalculator.getMaximum();
	}

	private static class HappinessCalculator {
		Map<String, Map<String, Integer>> happinessGuide = new HashMap<>();
		private static Pattern happinessLine = Pattern
				.compile("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+).");
		List<HappinessChain> chains = new ArrayList<>();
		private final boolean roundTrip;

		public HappinessCalculator(boolean roundTrip) {
			this.roundTrip = roundTrip;
		}

		public int getMaximum() {
			fillChains();
			Collections.sort(chains);
			chains.get(0).determineHappinessValue();
			return chains.get(0).getHappinessValue();
		}

		private void fillChains() {
			happinessGuide.keySet().stream().map(this::createBestValueChain).forEach(chains::add);
		}

		private HappinessChain createBestValueChain(String start) {
			List<String> ordered = new ArrayList<>();
			List<String> remaining = new ArrayList<>(happinessGuide.keySet());
			ordered.add(start);
			remaining.remove(start);
			String current = start;
			while (!remaining.isEmpty()) {
				String sortVal = current;
				remaining.sort((o1, o2) -> (getHappinessValue(sortVal, o2) - getHappinessValue(sortVal, o1)));
				String next = remaining.get(0);
				remaining.remove(next);
				ordered.add(next);
				current = next;
			}
			return new HappinessChain(ordered, roundTrip);
		}

		void parseAndAdd(String rule) {
			Matcher matcher = happinessLine.matcher(rule);
			matcher.matches();
			String source = matcher.group(1);
			String target = matcher.group(4);
			int multiplier = "lose".equals(matcher.group(2)) ? -1 : 1;
			int amount = Integer.parseInt(matcher.group(3)) * multiplier;
			happinessGuide.computeIfAbsent(source, k -> new HashMap<>()).put(target, amount);
		}

		private int getHappinessValue(String personA, String personB) {
			return happinessGuide.getOrDefault(personA, emptyMap()).getOrDefault(personB, 0)
					+ happinessGuide.getOrDefault(personB, emptyMap()).getOrDefault(personA, 0);
		}

		private class HappinessChain implements Comparable<HappinessChain> {
			private final List<String> order;
			private int happinessValue;
			private final boolean roundTrip;

			private HappinessChain(List<String> order, boolean roundTrip) {
				this.order = order;
				this.roundTrip = roundTrip;
				determineHappinessValue();
			}

			@Override
			public int compareTo(HappinessChain o) {
				return Integer.compare(o.happinessValue, happinessValue);
			}

			public int getHappinessValue() {
				return happinessValue;
			}

			private void determineHappinessValue() {
				if (order.isEmpty()) {
					happinessValue = 0;
				}
				happinessValue = IntStream.range(0, order.size()).map(i -> {
					if (!roundTrip && i == order.size() - 1) {
						return 0;
					}
					return i == order.size() - 1
							? HappinessCalculator.this.getHappinessValue(order.get(i), order.get(0))
							: HappinessCalculator.this.getHappinessValue(order.get(i), order.get(i + 1));
				}).sum();
			}
		}
	}

}
