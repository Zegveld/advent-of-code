package eu.helral.advent.of.code.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day11Test {

	@Test
	void part1() {
		System.out.println("Day11 part 1 result: " + new Day11().part1());
	}

	@Test
	void part2() {
		System.out.println("Day11 part 2 result: " + new Day11().part2());
	}

	@Test
	void getNextPassword() {
		assertThat(new Day11().getNextPassword("abcdefgh")).isEqualTo("abcdffaa");
		assertThat(new Day11().getNextPassword("ghijklmn")).isEqualTo("ghjaabcc");
	}
}
