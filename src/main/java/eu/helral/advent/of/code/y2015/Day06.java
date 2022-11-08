package eu.helral.advent.of.code.y2015;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.function.BiConsumer;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day06 extends DayTemplate {

	/**
	 * https://adventofcode.com/2015/day/6
	 *
	 * <pre>
	 * --- Day 6: Probably a Fire Hazard ---
	 * Because your neighbors keep defeating you in the holiday house decorating contest year after year,
	 * you've decided to deploy one million lights in a 1000x1000 grid.
	 *
	 * Furthermore, because you've been especially nice this year, Santa has mailed you instructions on
	 * how to display the ideal lighting configuration.
	 *
	 * Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at
	 * 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle
	 * various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners
	 * of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a
	 * 3x3 square. The lights all start turned off.
	 *
	 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions
	 * Santa sent you in order.
	 *
	 * For example:
	 *
	 * - turn on 0,0 through 999,999 would turn on (or leave on) every light.
	 * - toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on,
	 * and turning on the ones that were off.
	 * - turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
	 * After following the instructions, how many lights are lit?
	 *
	 * </pre>
	 */
	long part1() {
		Boolean[][] lights = new Boolean[1000][1000];
		for (Boolean[] bs : lights) {
			Arrays.fill(bs, false);
		}
		for (String action : getInput().toList()) {
			if (action.startsWith("turn on ")) {
				String[] fromTo = action.substring("turn on ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to, (x1, y1) -> lights[x1][y1] = true);
			} else if (action.startsWith("turn off ")) {
				String[] fromTo = action.substring("turn off ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to, (x1, y1) -> lights[x1][y1] = false);
			} else if (action.startsWith("toggle ")) {
				String[] fromTo = action.substring("toggle ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to, (x1, y1) -> lights[x1][y1] = !lights[x1][y1]);
			}
		}
		return Arrays.stream(lights).flatMap(Arrays::stream).filter(b -> b).count();
	}

	private void loopThroughLights(String[] from, String[] to, BiConsumer<Integer, Integer> action) {
		for (int x = Integer.parseInt(from[0]); x <= Integer.valueOf(to[0]); x++) {
			for (int y = Integer.parseInt(from[1]); y <= Integer.valueOf(to[1]); y++) {
				action.accept(x, y);
			}
		}
	}

	/**
	 * --- Part Two --- You just finish implementing your winning light pattern when
	 * you realize you mistranslated Santa's message from Ancient Nordic Elvish.
	 *
	 * The light grid you bought actually has individual brightness controls; each
	 * light can have a brightness of zero or more. The lights all start at zero.
	 *
	 * The phrase turn on actually means that you should increase the brightness of
	 * those lights by 1.
	 *
	 * The phrase turn off actually means that you should decrease the brightness of
	 * those lights by 1, to a minimum of zero.
	 *
	 * The phrase toggle actually means that you should increase the brightness of
	 * those lights by 2.
	 *
	 * What is the total brightness of all lights combined after following Santa's
	 * instructions?
	 *
	 * For example:
	 *
	 * turn on 0,0 through 0,0 would increase the total brightness by 1. toggle 0,0
	 * through 999,999 would increase the total brightness by 2000000.
	 */
	public BigInteger part2() {
		BigInteger[][] lights = new BigInteger[1000][1000];
		for (BigInteger[] bs : lights) {
			Arrays.fill(bs, BigInteger.ZERO);
		}
		for (String action : getInput().toList()) {
			if (action.startsWith("turn on ")) {
				String[] fromTo = action.substring("turn on ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to, (x1, y1) -> lights[x1][y1] = lights[x1][y1].add(BigInteger.ONE));
			} else if (action.startsWith("turn off ")) {
				String[] fromTo = action.substring("turn off ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to,
						(x1, y1) -> lights[x1][y1] = lights[x1][y1].intValue() == 0 ? BigInteger.ZERO
								: lights[x1][y1].subtract(BigInteger.ONE));
			} else if (action.startsWith("toggle ")) {
				String[] fromTo = action.substring("toggle ".length()).split(" through ");
				String[] from = fromTo[0].split(",");
				String[] to = fromTo[1].split(",");
				loopThroughLights(from, to, (x1, y1) -> lights[x1][y1] = lights[x1][y1].add(BigInteger.TWO));
			}
		}
		return Arrays.stream(lights).flatMap(Arrays::stream).reduce(BigInteger.ZERO, BigInteger::add);

	}
}
