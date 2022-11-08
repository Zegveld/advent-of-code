package eu.helral.advent.of.code.y2015;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day05 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
	 * Santa needs help figuring out which strings in his text file are naughty or nice.
	 *
	 * A nice string is one with all of the following properties:
	 *
	 * - It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
	 * - It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
	 * - It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
	 * For example:
	 *
	 * - ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
	 * - aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
	 * - jchzalrnumimnmhp is naughty because it has no double letter.
	 * - haegwjzuvuyypxyu is naughty because it contains the string xy.
	 * - dvszwmarrgswjxmb is naughty because it contains only one vowel.
	 * How many strings are nice?
	 *
	 * </pre>
	 */
	public long part1() {
		return getInput().filter(this::threeVowels).filter(this::atLeastOneTwiceInARow).filter(this::isNotNaughty1)
				.count();
	}

	private boolean threeVowels(String string1) {
		return string1.chars().filter(i -> i == 'a' || i == 'e' || i == 'i' || i == 'o' || i == 'u').count() >= 3;
	}

	private boolean atLeastOneTwiceInARow(String string1) {
		char lastChar = '-';
		for (char c : string1.toCharArray()) {
			if (c == lastChar) {
				return true;
			}
			lastChar = c;
		}
		return false;
	}

	private boolean isNotNaughty1(String string1) {
		return !string1.contains("ab") && !string1.contains("cd") && !string1.contains("pq") && !string1.contains("xy");
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.
	 *
	 * Now, a nice string is one with all of the following properties:
	 *
	 * - It contains a pair of any two letters that appears at least twice in the string without overlapping,
	 * like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
	 * - It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
	 * For example:
	 *
	 * - qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
	 * - xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
	 * - uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
	 * - ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
	 * How many strings are nice under these new rules?
	 *
	 * </pre>
	 */
	public long part2() {
		return getInput().filter(this::pairRepeatedTwice).filter(this::letterRepeatedWithOneInBetween).count();
	}

	private boolean pairRepeatedTwice(String string1) {
		String remaining = string1.substring(2);
		String search = string1.substring(0, 2);
		while (remaining.length() >= 2) {
			if (remaining.contains(search)) {
				return true;
			}
			search = search.substring(1) + remaining.charAt(0);
			remaining = remaining.substring(1);
		}
		return false;
	}

	private boolean letterRepeatedWithOneInBetween(String string1) {
		String remaining = string1;
		while (remaining.length() >= 3) {
			if (remaining.charAt(0) == remaining.charAt(2)) {
				return true;
			}
			remaining = remaining.substring(1);
		}
		return false;
	}

}
