package eu.helral.advent.of.code.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day08Test {

	@Test
	void part1() {
		System.out.println("Day08 part 1 result: " + new Day08().part1());
	}

	@Test
	void part2() {
		System.out.println("Day08 part 2 result: " + new Day08().part2());
	}

	@Test
	void counting() {
		assertThat(new Day08().countCharacterDifference("\"\"")).isEqualTo(2);
		assertThat(new Day08().countCharacterDifference("\"abc\"")).isEqualTo(2);
		assertThat(new Day08().countCharacterDifference("\"aaa\\\"aaa\"")).isEqualTo(3);
		assertThat(new Day08().countCharacterDifference("\"\\x27\"")).isEqualTo(5);
		assertThat(new Day08().countCharacterDifference("\"\\xff\"")).isEqualTo(5);
	}
}
