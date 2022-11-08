package eu.helral.advent.of.code.y2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day04 extends DayTemplate {

	MessageDigest MD5;

	Day04() throws NoSuchAlgorithmException {
		MD5 = MessageDigest.getInstance("MD5");
	}

	/**
	 * https://adventofcode.com/2015/day/4
	 *
	 * <pre>
	 * --- Day 4: The Ideal Stocking Stuffer --- Santa needs help mining some
	 * AdventCoins (very similar to bitcoins) to use as gifts for all the
	 * economically forward-thinking little girls and boys.
	 *
	 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at
	 * least five zeroes. The input to the MD5 hash is some secret key (your puzzle
	 * input, given below) followed by a number in decimal. To mine AdventCoins, you
	 * must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...)
	 * that produces such a hash.
	 *
	 * For example:
	 *
	 * - If your secret key is abcdef, the answer is 609043, because the MD5 hash of
	 * abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest
	 * such number to do so.
	 * - If your secret key is pqrstuv, the lowest number it combines with to make an
	 * MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of
	 * pqrstuv1048970 looks like 000006136ef....
	 * Your puzzle input is yzbqklnj.
	 * </pre>
	 */
	int part1() {
		return IntStream.range(0, Integer.MAX_VALUE).filter(i -> {
			String input = "yzbqklnj" + i;
			String hashed = new String(MD5.digest(input.getBytes()));
			if (hashed.charAt(0) == 0x00 && hashed.charAt(1) == 0x00 && (hashed.charAt(2) & 0xf0) == 0x00) {
				return true;
			}
			return false;
		}).findFirst().getAsInt();
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * Now find one that starts with six zeroes.
	 * </pre>
	 */
	int part2() {
		return IntStream.range(0, Integer.MAX_VALUE).filter(i -> {
			String input = "yzbqklnj" + i;
			String hashed = new String(MD5.digest(input.getBytes()));
			if (hashed.charAt(0) == 0x00 && hashed.charAt(1) == 0x00 && hashed.charAt(2) == 0x00) {
				return true;
			}
			return false;
		}).findFirst().getAsInt();
	}
}
