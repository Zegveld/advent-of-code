package eu.helral.advent.of.code.utils.path.shortest;

import static java.util.Comparator.reverseOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ShortestPath {
	Map<String, Map<String, BigDecimal>> routeMap = new HashMap<>();
	Set<String> allLocations = new HashSet<>();

	public ShortestPath(Collection<Route> routes) {
		for (Route route : routes) {
			allLocations.add(route.from());
			allLocations.add(route.to());
			routeMap.computeIfAbsent(route.from(), k -> new HashMap<>()).put(route.to(),
					BigDecimal.valueOf(route.distance()));
			if (route.bidirectional()) {
				routeMap.computeIfAbsent(route.to(), k -> new HashMap<>()).put(route.from(),
						BigDecimal.valueOf(route.distance()));
			}
		}
	}

	RouteInfo findShortestPath(String from, String to) {
		List<RouteInfo> routes = new ArrayList<>();
		routes.add(new RouteInfo(from));
		while (!routes.isEmpty() && !to.equals(routes.get(0).getCurrentLocation())) {
			RouteInfo current = routes.remove(0);
			String currentLocation = current.getCurrentLocation();
			for (Entry<String, BigDecimal> target : routeMap.get(currentLocation).entrySet()) {
				routes.add(current.updateNewWith(target.getKey(), target.getValue()));
			}
			routes.sort(RouteInfo::compareTo);
		}
		return routes.get(0);
	}

	public RouteInfo findShortestPathVisitingAllLocations() {
		List<RouteInfo> routes = new ArrayList<>();
		for (String from : routeMap.keySet()) {
			routes.add(new RouteInfo(from));
		}
		while (!routes.isEmpty() && !visitedAllLocations(routes.get(0))) {
			RouteInfo current = routes.remove(0);
			String currentLocation = current.getCurrentLocation();
			for (Entry<String, BigDecimal> target : routeMap.get(currentLocation).entrySet()) {
				routes.add(current.updateNewWith(target.getKey(), target.getValue()));
			}
			routes.sort(RouteInfo::compareTo);
		}
		return routes.get(0);
	}

	public RouteInfo findShortestPathVisitingAllLocationsOnce() {
		List<RouteInfo> routes = new ArrayList<>();
		for (String from : routeMap.keySet()) {
			routes.add(new RouteInfo(from));
		}
		while (!routes.isEmpty() && !visitedAllLocations(routes.get(0))) {
			RouteInfo current = routes.remove(0);
			String currentLocation = current.getCurrentLocation();
			for (Entry<String, BigDecimal> target : routeMap.get(currentLocation).entrySet()) {
				if (!current.route.contains(target.getKey())) {
					routes.add(current.updateNewWith(target.getKey(), target.getValue()));
				}
			}
			routes.sort(RouteInfo::compareTo);
		}
		return routes.get(0);
	}

	public RouteInfo findLongestPathVisitingAllLocationsOnce() {
		List<RouteInfo> routes = new ArrayList<>();
		List<RouteInfo> completedRoutes = new ArrayList<>();
		for (String from : routeMap.keySet()) {
			routes.add(new RouteInfo(from));
		}
		while (!routes.isEmpty()) {
			RouteInfo current = routes.remove(0);
			String currentLocation = current.getCurrentLocation();
			for (Entry<String, BigDecimal> target : routeMap.get(currentLocation).entrySet()) {
				if (!current.route.contains(target.getKey())) {
					RouteInfo newRoute = current.updateNewWith(target.getKey(), target.getValue());
					if (visitedAllLocations(newRoute)) {
						completedRoutes.add(newRoute);
					} else {
						routes.add(newRoute);
					}
				}
			}
			routes.sort(reverseOrder());
		}
		completedRoutes.sort(reverseOrder());
		return completedRoutes.get(0);
	}

	private boolean visitedAllLocations(RouteInfo routeInfo) {
		ArrayList<String> remainingLocations = new ArrayList<>(allLocations);
		remainingLocations.removeAll(routeInfo.route);
		return remainingLocations.isEmpty();
	}

	public class RouteInfo implements Comparable<RouteInfo> {
		List<String> route = new ArrayList<>();
		BigDecimal totalDistance;

		RouteInfo(String start) {
			route.add(start);
			totalDistance = BigDecimal.ZERO;
		}

		RouteInfo(RouteInfo previous, String next, BigDecimal distance) {
			route = new ArrayList<>(previous.route);
			route.add(next);
			totalDistance = previous.totalDistance.add(distance);
		}

		public String getCurrentLocation() {
			return route.get(route.size() - 1);
		}

		RouteInfo updateNewWith(String next, BigDecimal distance) {
			return new RouteInfo(this, next, distance);
		}

		@Override
		public int compareTo(RouteInfo o) {
			int compare = totalDistance.compareTo(o.totalDistance);
			if (compare == 0) {
				compare = Integer.compare(route.size(), o.route.size());
			}
			if (compare == 0) {
				compare = Arrays.compare(route.toArray(String[]::new), o.route.toArray(String[]::new),
						String::compareTo);
			}
			return compare;
		}

		public BigDecimal getDistance() {
			return totalDistance;
		}
	}
}
