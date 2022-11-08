package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day07 extends DayTemplate {
	/**
	 * https://adventofcode.com/2015/day/7
	 */
	private static final Pattern NOT = Pattern.compile("NOT (\\w+) -> (\\w+)");

	private static final Pattern OR_DIRECT_DIRECT = Pattern.compile("(\\w+) OR (\\w+) -> (\\w+)");

	private static final Pattern AND_DIRECT_DIRECT = Pattern.compile("(\\w+) AND (\\w+) -> (\\w+)");

	private static final Pattern AND_DIRECT_NUMBER = Pattern.compile("(\\w+) AND (\\d+) -> (\\w+)");

	private static final Pattern AND_NUMBER_DIRECT = Pattern.compile("(\\d+) AND (\\w+) -> (\\w+)");

	private static final Pattern RSHIFT = Pattern.compile("(\\w+) RSHIFT (\\d+) -> (\\w+)");

	private static final Pattern LSHIFT = Pattern.compile("(\\w+) LSHIFT (\\d+) -> (\\w+)");

	private static final Pattern ASSIGN_DIRECT = Pattern.compile("(\\w+) -> (\\w+)");

	private static final Pattern ASSIGN_NUMBER = Pattern.compile("(\\d+) -> (\\w+)");

	private final Map<String, Integer> outputMap = new HashMap<>();

	private final Map<Pattern, InputHandler> handlers = Map.of( //
			ASSIGN_NUMBER, this::assignNumber, ASSIGN_DIRECT, this::assignDirect, LSHIFT, this::lShift, RSHIFT,
			this::rShift, AND_NUMBER_DIRECT, this::andNumberDirect, AND_DIRECT_NUMBER, this::andDirectNumber,
			AND_DIRECT_DIRECT, this::andDirectDirect, OR_DIRECT_DIRECT, this::orDirectDirect, NOT, this::not);

	private boolean not(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1))) {
			store(matcher.group(2), outputMap.get(matcher.group(1)) ^ 0xffff);
			return true;
		}
		return false;
	}

	private boolean orDirectDirect(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1)) && outputMap.containsKey(matcher.group(2))) {
			store(matcher.group(3), outputMap.get(matcher.group(1)) | outputMap.get(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean andDirectDirect(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1)) && outputMap.containsKey(matcher.group(2))) {
			store(matcher.group(3), outputMap.get(matcher.group(1)) & outputMap.get(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean andDirectNumber(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1))) {
			store(matcher.group(3), outputMap.get(matcher.group(1)) & Integer.valueOf(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean andNumberDirect(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(2))) {
			store(matcher.group(3), Integer.valueOf(matcher.group(1)) & outputMap.get(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean rShift(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1))) {
			store(matcher.group(3), outputMap.get(matcher.group(1)) >> Integer.valueOf(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean lShift(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1))) {
			store(matcher.group(3), outputMap.get(matcher.group(1)) << Integer.valueOf(matcher.group(2)));
			return true;
		}
		return false;
	}

	private boolean assignDirect(Matcher matcher) {
		if (outputMap.containsKey(matcher.group(1))) {
			store(matcher.group(2), outputMap.get(matcher.group(1)));
			return true;
		}
		return false;
	}

	private boolean assignNumber(Matcher matcher) {
		store(matcher.group(2), Integer.valueOf(matcher.group(1)));
		return true;
	}

	/**
	 * <pre>
	 * --- Day 7: Some Assembly Required ---
	 * This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is a
	 * little under the recommended age range, and he needs help assembling the circuit.
	 *
	 * Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A signal
	 * is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal from one source,
	 * but can provide its signal to multiple destinations. A gate provides no signal until all of its inputs have a signal.
	 *
	 * The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires x and y
	 * to an AND gate, and then connect its output to wire z.
	 *
	 * For example:
	 *
	 * - 123 -> x means that the signal 123 is provided to wire x.
	 * - x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
	 * - p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
	 * - NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
	 * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate the
	 * circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.
	 *
	 * For example, here is a simple circuit:
	 *
	 * 123 -> x
	 * 456 -> y
	 * x AND y -> d
	 * x OR y -> e
	 * x LSHIFT 2 -> f
	 * y RSHIFT 2 -> g
	 * NOT x -> h
	 * NOT y -> i
	 * After it is run, these are the signals on the wires:
	 *
	 * d: 72
	 * e: 507
	 * f: 492
	 * g: 114
	 * h: 65412
	 * i: 65079
	 * x: 123
	 * y: 456
	 * In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?
	 *
	 *
	 * </pre>
	 */
	public Integer part1() {
		List<String> unhandledLines = new ArrayList<>();
		List<String> currentList = getInput().toList();
		while (!currentList.isEmpty()) {
			for (String line : currentList) {
				boolean handled = handle(line);
				if (!handled) {
					unhandledLines.add(line);
				}
			}
			if (currentList.size() == unhandledLines.size()) {
				outputMap.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
				System.out.println();
				System.out.println();
				System.out.println("unhandled lines:");
				System.out.println();
				currentList.forEach(System.out::println);
				return Integer.MIN_VALUE;
			}
			currentList = unhandledLines;
			unhandledLines = new ArrayList<>();
		}
		outputMap.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
		return outputMap.get("a");
	}

	private void store(String key, Integer value) {
		outputMap.put(key, value % 65536);
	}

	private boolean handle(String line) {
		for (Entry<Pattern, InputHandler> handler : handlers.entrySet()) {
			Matcher matcher = handler.getKey().matcher(line);
			if (matcher.matches()) {
				return handler.getValue().handle(matcher);
			}
		}
		return false;
	}

	private interface InputHandler {
		boolean handle(Matcher lineMatcher);
	}

	public Integer part2() {
		List<String> unhandledLines = new ArrayList<>();
		List<String> currentList = new ArrayList<>(getInput().toList());
		currentList.remove("19138 -> b");
		currentList.add("16076 -> b");
		while (!currentList.isEmpty()) {
			for (String line : currentList) {
				boolean handled = handle(line);
				if (!handled) {
					unhandledLines.add(line);
				}
			}
			if (currentList.size() == unhandledLines.size()) {
				outputMap.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
				System.out.println();
				System.out.println();
				System.out.println("unhandled lines:");
				System.out.println();
				currentList.forEach(System.out::println);
				return Integer.MIN_VALUE;
			}
			currentList = unhandledLines;
			unhandledLines = new ArrayList<>();
		}
		outputMap.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
		return outputMap.get("a");
	}

}
