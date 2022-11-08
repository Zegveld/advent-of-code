package eu.helral.advent.of.code.utils.path.shortest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import eu.helral.advent.of.code.utils.path.shortest.ShortestPath.RouteInfo;

class ShortestPathTest {

	@Test
	void eenRoute() {
		Route route = new Route("A", "B", 12, true);

		RouteInfo shortestPath = new ShortestPath(Set.of(route)).findShortestPath("A", "B");

		assertThat(shortestPath.totalDistance).isEqualTo(BigDecimal.valueOf(12));
	}

	@Test
	void drieRoutes_directeRouteKortste() {
		Route route1 = new Route("A", "C", 12, true);
		Route route2 = new Route("A", "B", 12, true);
		Route route3 = new Route("B", "C", 12, true);

		RouteInfo shortestPath = new ShortestPath(Set.of(route1, route2, route3)).findShortestPath("A", "C");

		assertThat(shortestPath.totalDistance).isEqualTo(BigDecimal.valueOf(12));
	}

	@Test
	void drieRoutes_directeRouteLangste() {
		Route route1 = new Route("A", "C", 120, true);
		Route route2 = new Route("A", "B", 12, true);
		Route route3 = new Route("B", "C", 12, true);

		RouteInfo shortestPath = new ShortestPath(Set.of(route1, route2, route3)).findShortestPath("A", "C");

		assertThat(shortestPath.totalDistance).isEqualTo(BigDecimal.valueOf(24));
	}

	@Test
	void drieRoutes_ABC() {
		Route route1 = new Route("A", "C", 120, true);
		Route route2 = new Route("A", "B", 12, true);
		Route route3 = new Route("B", "C", 12, true);

		RouteInfo shortestPath = new ShortestPath(Set.of(route1, route2, route3))
				.findShortestPathVisitingAllLocations();

		assertThat(shortestPath.totalDistance).isEqualTo(BigDecimal.valueOf(24));
	}

}
