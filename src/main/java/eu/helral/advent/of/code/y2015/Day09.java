package eu.helral.advent.of.code.y2015;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.helral.advent.of.code.template.DayTemplate;
import eu.helral.advent.of.code.utils.path.shortest.Route;
import eu.helral.advent.of.code.utils.path.shortest.ShortestPath;

public class Day09 extends DayTemplate {

	/**
	 * https://adventofcode.com/2015/day/9
	 *
	 * <pre>
	 * --- Day 9: All in a Single Night ---
	 * Every year, Santa manages to deliver all of his presents in a single night.
	 *
	 * This year, however, he has some new locations to visit; his elves have provided him the
	 * distances between every pair of locations. He can start and end at any two (different)
	 * locations he wants, but he must visit each location exactly once. What is the shortest
	 * distance he can travel to achieve this?
	 *
	 * For example, given the following distances:
	 *
	 * - London to Dublin = 464
	 * - London to Belfast = 518
	 * - Dublin to Belfast = 141
	 * The possible routes are therefore:
	 *
	 * - Dublin -> London -> Belfast = 982
	 * - London -> Dublin -> Belfast = 605
	 * - London -> Belfast -> Dublin = 659
	 * - Dublin -> Belfast -> London = 659
	 * - Belfast -> Dublin -> London = 605
	 * - Belfast -> London -> Dublin = 982
	 * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
	 *
	 * What is the distance of the shortest route?
	 *
	 *
	 * </pre>
	 */
	public BigDecimal part1() {
		ShortestPath shortestPath = new ShortestPath(getInput().map(this::toRoute).toList());
		return shortestPath.findShortestPathVisitingAllLocationsOnce().getDistance();
	}

	public BigDecimal part2() {
		ShortestPath shortestPath = new ShortestPath(getInput().map(this::toRoute).toList());
		return shortestPath.findLongestPathVisitingAllLocationsOnce().getDistance();
	}

	private static Pattern routePattern = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

	private Route toRoute(String input) {
		Matcher matcher = routePattern.matcher(input);
		if (matcher.matches()) {
			return new Route(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)), true);
		}
		throw new IllegalStateException("expecting all lines to match.");
	}

}
