package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.List;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day17 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 17: No Such Thing as Too Much ---
	 *
	 * The elves bought too much eggnog again - 150 liters this time. To fit it all
	 * into your refrigerator, you'll need to move it into smaller containers. You
	 * take an inventory of the capacities of the available containers.
	 *
	 * For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters.
	 * If you need to store 25 liters, there are four ways to do it:
	 *
	 * 15 and 10
	 * 20 and 5 (the first 5)
	 * 20 and 5 (the second 5)
	 * 15, 5, and 5
	 *
	 * Filling all containers entirely, how many different combinations of
	 * containers can exactly fit all 150 liters of eggnog?
	 * </pre>
	 *
	 */
	public int part1() {
		List<Integer> list = getInput().map(Integer::parseInt).toList();
		return count(new ArrayList<>(), 0, list);
	}

	private int count(ArrayList<Integer> previousNumbers, int start, List<Integer> list) {
		int counter = 0;
		for (int current = start; current < list.size(); current++) {
			ArrayList<Integer> next = new ArrayList<>(previousNumbers);
			next.add(list.get(current));
			int currentSum = next.stream().mapToInt(i -> i).sum();
			if (currentSum == 150) {
				counter++;
			} else if (next.stream().mapToInt(i -> i).sum() < 150) {
				counter += count(next, current + 1, list);
			}
		}
		return counter;
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * While playing with all the containers in the kitchen, another load of eggnog arrives!
	 * The shipping and receiving department is requesting as many containers as you can spare.
	 *
	 * Find the minimum number of containers that can exactly fit all 150 liters of eggnog. How
	 * many different ways can you fill that number of containers and still hold exactly 150 litres?
	 *
	 * In the example above, the minimum number of containers was two. There were three ways to use
	 * that many containers, and so the answer there would be 3.
	 * </pre>
	 */
	public int part2() {
		List<Integer> list = getInput().map(Integer::parseInt).toList();
		return countLimit(new ArrayList<>(), 0, list, count2(new ArrayList<>(), 0, list));
	}

	private int countLimit(ArrayList<Integer> previousNumbers, int start, List<Integer> list, int limit) {
		int counter = 0;
		for (int current = start; current < list.size(); current++) {
			ArrayList<Integer> next = new ArrayList<>(previousNumbers);
			next.add(list.get(current));
			int currentSum = next.stream().mapToInt(i -> i).sum();
			if (currentSum == 150) {
				counter++;
			} else if (next.stream().mapToInt(i -> i).sum() < 150 && next.size() < limit) {
				counter += countLimit(next, current + 1, list, limit);
			}
		}
		return counter;
	}

	private int count2(ArrayList<Integer> previousNumbers, int start, List<Integer> list) {
		int containerCount = list.size();
		for (int current = start; current < list.size(); current++) {
			ArrayList<Integer> next = new ArrayList<>(previousNumbers);
			next.add(list.get(current));
			int currentSum = next.stream().mapToInt(i -> i).sum();
			if (currentSum == 150) {
				if (next.size() < containerCount) {
					containerCount = next.size();
				}
			} else if (next.stream().mapToInt(i -> i).sum() < 150) {
				int currentAmount = count2(next, current + 1, list);
				if (currentAmount < containerCount) {
					containerCount = currentAmount;
				}
			}
		}
		return containerCount;
	}

}
