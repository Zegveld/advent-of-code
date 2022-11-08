package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.List;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day03 extends DayTemplate {

	/**
	 * https://adventofcode.com/2015/day/3
	 *
	 * <pre>
	 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
	 * Santa is delivering presents to an infinite two-dimensional grid of houses.
	 *
	 * He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and
	 * tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move,
	 * he delivers another present to the house at his new location.
	 *
	 * However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up
	 * visiting some houses more than once. How many houses receive at least one present?
	 *
	 * For example:
	 *
	 * - > delivers presents to 2 houses: one at the starting location, and one to the east.
	 * - ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
	 * - ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
	 * </pre>
	 */
	public long part1() {
		return getInput().mapToLong(this::countVisits).sum();
	}

	List<String> handledMovements = new ArrayList<>();

	private long countVisits(String movementList) {
		String movement = "";
		handledMovements.add(movement);
		for (char c : movementList.toCharArray()) {
			if (c == '>') {
				if (movement.contains("<")) {
					movement = movement.substring(0, movement.length() - 1);
				} else {
					movement = movement + ">";
				}
			} else if (c == '<') {
				if (movement.contains(">")) {
					movement = movement.substring(0, movement.length() - 1);
				} else {
					movement = movement + "<";
				}
			} else if (c == 'v') {
				if (movement.contains("^")) {
					movement = movement.substring(1);
				} else {
					movement = "v" + movement;
				}
			} else if (c == '^') {
				if (movement.contains("v")) {
					movement = movement.substring(1);
				} else {
					movement = "^" + movement;
				}
			}
			handledMovements.add(movement);
		}
		return handledMovements.stream().distinct().count();
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.
	 *
	 * Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving
	 * based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.
	 *
	 * This year, how many houses receive at least one present?
	 *
	 * For example:
	 *
	 * - ^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
	 * - ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
	 * - ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
	 * </pre>
	 */
	public long part2() {
		return getInput().mapToLong(this::countVisits2).sum();
	}

	private long countVisits2(String movementList) {
		String movementS = "";
		String movementR = "";
		handledMovements.add("");
		boolean roboTurn = false;
		for (char c : movementList.toCharArray()) {
			roboTurn = !roboTurn;
			String movement = roboTurn ? movementR : movementS;
			if (c == '>') {
				if (movement.contains("<")) {
					movement = movement.substring(0, movement.length() - 1);
				} else {
					movement = movement + ">";
				}
			} else if (c == '<') {
				if (movement.contains(">")) {
					movement = movement.substring(0, movement.length() - 1);
				} else {
					movement = movement + "<";
				}
			} else if (c == 'v') {
				if (movement.contains("^")) {
					movement = movement.substring(1);
				} else {
					movement = "v" + movement;
				}
			} else if (c == '^') {
				if (movement.contains("v")) {
					movement = movement.substring(1);
				} else {
					movement = "^" + movement;
				}
			}
			handledMovements.add(movement);
			if (roboTurn) {
				movementR = movement;
			} else {
				movementS = movement;
			}
		}
		return handledMovements.stream().distinct().count();
	}

}
