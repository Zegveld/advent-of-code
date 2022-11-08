package eu.helral.advent.of.code.y2015;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day12 extends DayTemplate {

	Pattern number = Pattern.compile("\\-?\\d+");

	public long part1() {
		return getInput().map(number::matcher).mapToLong(this::sumMatches).sum();
	}

	public long part2() {
		return getInput().map(this::removeRedBlocks).map(number::matcher).mapToLong(this::sumMatches).sum();
	}

	private long sumMatches(Matcher matcher1) {
		long result = 0;
		while (matcher1.find()) {
			result += Long.parseLong(matcher1.group());
		}
		return result;
	}

	private String removeRedBlocks(String string1) {
		String result = string1;
		int indexOf = result.indexOf("\"red\"");
		while (indexOf != -1) {
			if (result.charAt(indexOf - 1) == ',' || result.charAt(indexOf - 1) == '[') {
				result = result.substring(0, indexOf) + result.substring(indexOf + 3);
				indexOf = result.indexOf("\"red\"");
				continue;
			}
			int skip = 0;
			outer: for (int i = indexOf; i >= 0; i--) {
				if (result.charAt(i) == '}') {
					skip++;
				} else if (result.charAt(i) == '{') {
					if (skip > 0) {
						skip--;
						continue;
					}
					for (int j = indexOf; j <= result.length(); j++) {
						if (result.charAt(j) == '{') {
							skip++;
						} else if (result.charAt(j) == '}') {
							if (skip > 0) {
								skip--;
								continue;
							}
							result = result.substring(0, i + 1) + result.substring(j);
							break outer;
						}
					}
				}
			}
			indexOf = result.indexOf("\"red\"");
		}
		return result;
	}
}
