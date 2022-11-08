package eu.helral.advent.of.code.y2015;

import java.util.Objects;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day11 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 11: Corporate Policy ---
	 * Santa's previous password expired, and he needs help choosing a new one.
	 *
	 * To help him remember his new password after the old one expires, Santa has devised a method of coming up with a password based on the previous one. Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons), so he finds his new password by incrementing his old password string repeatedly until it is valid.
	 *
	 * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around.
	 *
	 * Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:
	 *
	 * - Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
	 * - Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
	 * - Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
	 * For example:
	 *
	 * - hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
	 * - abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
	 * - abbcegjk fails the third requirement, because it only has one double letter (bb).
	 * The next password after abcdefgh is abcdffaa.
	 * The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.
	 * Given Santa's current password (your puzzle input), what should his next password be?
	 * </pre>
	 *
	 * @return
	 */
	public String part1() {
		return getNextPassword("vzbxkghb");
	}

	String getNextPassword(String input) {
		String nextVal = getNext(input);
		while (!isValid(nextVal)) {
			nextVal = getNext(nextVal);
		}
		return nextVal;
	}

	private boolean isValid(String nextVal) {
		return noIllegalCharacter(nextVal) && containsTwoPairs(nextVal) && containsSequenceOfThree(nextVal);
	}

	private boolean containsSequenceOfThree(String nextVal) {
		int count = 0;
		Character previousChar = null;
		for (Character c : nextVal.toCharArray()) {
			if (previousChar != null && c - 1 == previousChar) {
				count++;
				if (count == 3) {
					return true;
				}
			} else {
				count = 1;
			}
			previousChar = c;
		}
		return false;
	}

	private boolean containsTwoPairs(String nextVal) {
		Character firstGroup = null;
		Character previousChar = null;
		for (Character c : nextVal.toCharArray()) {
			if (Objects.equals(previousChar, c)) {
				if (firstGroup != null && firstGroup != c) {
					return true;
				}
				firstGroup = c;
			}
			previousChar = c;
		}
		return false;
	}

	private boolean noIllegalCharacter(String nextVal) {
		return nextVal.indexOf('i') == -1 && nextVal.indexOf('l') == -1 && nextVal.indexOf('o') == -1;
	}

	private String getNext(String input) {
		int i = 1;
		for (; i <= input.length(); i++) {
			if (input.charAt(input.length() - i) != 'z') {
				String result = input.substring(0, input.length() - i) + (char) (input.charAt(input.length() - i) + 1);
				while (result.length() < input.length()) {
					result = result + "a";
				}
				return result;
			}
		}
		return null;
	}

	public String part2() {
		return getNextPassword(getNextPassword("vzbxkghb"));
	}

}
