package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day18 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 18: Like a GIF For Your Yard ---
	 * After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed.
	 * You arrange them in a 100x100 grid.
	 *
	 * Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights,
	 * he says, you'll have to resort to animation.
	 *
	 * Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".
	 *
	 * Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each light's next
	 * state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals).
	 * Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
	 *
	 * For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, which is on
	 * an edge, only has the neighbors marked 1 through 5:
	 *
	 * 1B5...
	 * 234...
	 * ......
	 * ..123.
	 * ..8A4.
	 * ..765.
	 * The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
	 *
	 * A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
	 * A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
	 * All of the lights update simultaneously; they all consider the same current state before moving to the next.
	 *
	 * Here's a few steps from an example configuration of another 6x6 grid:
	 *
	 * Initial state:
	 * .#.#.#
	 * ...##.
	 * #....#
	 * ..#...
	 * #.#..#
	 * ####..
	 *
	 * After 1 step:
	 * ..##..
	 * ..##.#
	 * ...##.
	 * ......
	 * #.....
	 * #.##..
	 *
	 * After 2 steps:
	 * ..###.
	 * ......
	 * ..###.
	 * ......
	 * .#....
	 * .#....
	 *
	 * After 3 steps:
	 * ...#..
	 * ......
	 * ...#..
	 * ..##..
	 * ......
	 * ......
	 *
	 * After 4 steps:
	 * ......
	 * ......
	 * ..##..
	 * ..##..
	 * ......
	 * ......
	 * After 4 steps, this example has four lights on.
	 *
	 * In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
	 *
	 * </pre>
	 */
	public long part1() {
		LightGrid grid = new LightGrid();
		List<String> initialState = getInput().toList();
		for (int i = 0; i < initialState.size(); i++) {
			String currentLine = initialState.get(i);
			for (int j = 0; j < currentLine.length(); j++) {
				if (currentLine.charAt(j) == '#') {
					grid.toggle(i, j);
				}
			}
		}
		int steps = 100;
		for (int i = 0; i < steps; i++) {
			List<Runnable> actions = grid.getLights().map(this::getToggleAction).toList();
			actions.forEach(Runnable::run);
		}
		return grid.getLights().filter(Light::isLit).count();
	}

	public Runnable getToggleAction(Light light) {
		long litNeighbors = light.neighbors.stream().filter(Light::isLit).count();
		if (!light.isLit() && litNeighbors == 3) {
			return light::toggle;
		}
		if (light.isLit() && litNeighbors != 2 && litNeighbors != 3) {
			return light::toggle;
		}
		return () -> {
		};
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * You flip the instructions over; Santa goes on to point out that this is all just an implementation of
	 * Conway's Game of Life. At least, it was, until you notice that something's wrong with the grid of lights
	 * you bought: four lights, one in each corner, are stuck on and can't be turned off. The example above will
	 * actually run like this:
	 *
	 * Initial state:
	 * ##.#.#
	 * ...##.
	 * #....#
	 * ..#...
	 * #.#..#
	 * ####.#
	 *
	 * After 1 step:
	 * #.##.#
	 * ####.#
	 * ...##.
	 * ......
	 * #...#.
	 * #.####
	 *
	 * After 2 steps:
	 * #..#.#
	 * #....#
	 * .#.##.
	 * ...##.
	 * .#..##
	 * ##.###
	 *
	 * After 3 steps:
	 * #...##
	 * ####.#
	 * ..##.#
	 * ......
	 * ##....
	 * ####.#
	 *
	 * After 4 steps:
	 * #.####
	 * #....#
	 * ...#..
	 * .##...
	 * #.....
	 * #.#..#
	 *
	 * After 5 steps:
	 * ##.###
	 * .##..#
	 * .##...
	 * .##...
	 * #.#...
	 * ##...#
	 * After 5 steps, this example now has 17 lights on.
	 *
	 * In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the
	 * on state, how many lights are on after 100 steps?
	 *
	 * </pre>
	 */
	public long part2() {
		LightGrid grid = new LightGrid();
		List<String> initialState = getInput().toList();
		for (int i = 0; i < initialState.size(); i++) {
			String currentLine = initialState.get(i);
			for (int j = 0; j < currentLine.length(); j++) {
				if (currentLine.charAt(j) == '#') {
					grid.toggle(i, j);
				}
			}
		}
		grid.turnCornersOn();
		int steps = 100;
		for (int i = 0; i < steps; i++) {
			List<Runnable> actions = grid.getLights().map(this::getToggleAction).toList();
			actions.forEach(Runnable::run);
			grid.turnCornersOn();
		}
		return grid.getLights().filter(Light::isLit).count();
	}

	public class LightGrid {
		Light[][] grid;

		Stream<Light> getLights() {
			return Arrays.stream(grid).flatMap(Arrays::stream);
		}

		public void turnCornersOn() {
			if (!grid[0][0].isLit()) {
				grid[0][0].toggle();
			}
			if (!grid[0][99].isLit()) {
				grid[0][99].toggle();
			}
			if (!grid[99][0].isLit()) {
				grid[99][0].toggle();
			}
			if (!grid[99][99].isLit()) {
				grid[99][99].toggle();
			}
		}

		public void toggle(int i, int j) {
			grid[i][j].toggle();
		}

		public LightGrid() {
			grid = new Light[100][100];
			for (int x = 0; x < 100; x++) {
				for (int y = 0; y < 100; y++) {
					grid[x][y] = new Light();
				}
			}
			for (int x = 0; x < 100; x++) {
				for (int y = 0; y < 100; y++) {
					if (y > 0) {
						grid[x][y].addNeighbor(grid[x][y - 1]);
					}
					if (y < 99) {
						grid[x][y].addNeighbor(grid[x][y + 1]);
					}
					if (x > 0) {
						grid[x][y].addNeighbor(grid[x - 1][y]);
					}
					if (x < 99) {
						grid[x][y].addNeighbor(grid[x + 1][y]);
					}
					if (x > 0 && y > 0) {
						grid[x][y].addNeighbor(grid[x - 1][y - 1]);
					}
					if (x > 0 && y < 99) {
						grid[x][y].addNeighbor(grid[x - 1][y + 1]);
					}
					if (x < 99 && y > 0) {
						grid[x][y].addNeighbor(grid[x + 1][y - 1]);
					}
					if (x < 99 && y < 99) {
						grid[x][y].addNeighbor(grid[x + 1][y + 1]);
					}
				}
			}
		}
	}

	private static class Light {
		private final List<Light> neighbors = new ArrayList<>();
		private boolean lit = false;

		public boolean isLit() {
			return lit;
		}

		void addNeighbor(Light light) {
			neighbors.add(light);
		}

		void toggle() {
			lit = !lit;
		}
	}
}
