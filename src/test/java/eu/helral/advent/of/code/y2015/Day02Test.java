package eu.helral.advent.of.code.y2015;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day02Test {

	@Test
	void part1() {
		long result = new Day02().part1();
		assertThat(result).isNotEqualTo(1591134);
		System.out.println("Day01 part 1 result: " + result);
	}

	@Test
	void examples() {
		assertThat(new Day02().calculatePaper("2x3x4")).isEqualTo(58);
		assertThat(new Day02().calculatePaper("5x5x1")).isEqualTo(75);
		assertThat(new Day02().calculatePaper("5x1x5")).isEqualTo(75);
		assertThat(new Day02().calculatePaper("1x5x5")).isEqualTo(75);
		assertThat(new Day02().calculatePaper("10x9x8")).isEqualTo(556);
		assertThat(new Day02().calculatePaper("0x9x8")).isEqualTo(0);
	}

	@Test
	void part2() {
		System.out.println("Day01 part 2 result: " + new Day02().part2());
	}

}
