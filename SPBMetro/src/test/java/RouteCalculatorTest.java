import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class RouteCalculatorTest extends TestCase {

  List<Station> route;
  StationIndex stationIndex;
  public RouteCalculator routeCalculator;

  @Override
  protected void setUp() throws Exception {

    Line line1 = new Line(1, "Первая");
    Line line2 = new Line(2, "Вторая");
    Line line3 = new Line(3, "Третья");
    Line line5 = new Line(5, "Пятая");
    Station station1 = new Station("Василеостровская", line3);
    Station station2 = new Station("Гостиный двор", line3);
    Station station3 = new Station("Маяковская", line3);
    Station station4 = new Station("Площадь восстания", line1);
    Station station5 = new Station("Владимирская", line1);
    Station station6 = new Station("Пушкинская", line1);
    Station station7 = new Station("Звенигородская", line5);
    Station station8 = new Station("Обводный канал", line5);
    Station station9 = new Station("Невский проспект", line2);
    Station station10 = new Station("Горьковская", line2);
    line1.addStation(station4);
    line1.addStation(station5);
    line1.addStation(station6);
    line2.addStation(station9);
    line2.addStation(station10);
    line3.addStation(station1);
    line3.addStation(station2);
    line3.addStation(station3);
    line5.addStation(station7);
    line5.addStation(station8);
    stationIndex = new StationIndex();
    stationIndex.addLine(line1);
    stationIndex.addLine(line2);
    stationIndex.addLine(line3);
    stationIndex.addLine(line5);
    stationIndex.addStation(station1);
    stationIndex.addStation(station2);
    stationIndex.addStation(station3);
    stationIndex.addStation(station4);
    stationIndex.addStation(station5);
    stationIndex.addStation(station6);
    stationIndex.addStation(station7);
    stationIndex.addStation(station8);
    stationIndex.addStation(station9);
    stationIndex.addStation(station10);
    List<Station> connection1 = new ArrayList<>();
    connection1.add(station2);
    connection1.add(station9);
    List<Station> connection2 = new ArrayList<>();
    connection2.add(station3);
    connection2.add(station4);
    List<Station> connection3 = new ArrayList<>();
    connection3.add(station6);
    connection3.add(station7);
    stationIndex.addConnection(connection1);
    stationIndex.addConnection(connection2);
    stationIndex.addConnection(connection3);
    routeCalculator = new RouteCalculator(stationIndex);
    route = new ArrayList<>();
    route.add(station1);
    route.add(station2);
    route.add(station3);
    route.add(station4);
  }

  public void testCalculateDuration() {
    double actual = RouteCalculator.calculateDuration(route);
    double expected = 8.5;
    assertEquals(expected, actual);
  }

  public void testGetRouteOnTheLine() {
    Station from = stationIndex.getStation("Площадь восстания");
    Station to = stationIndex.getStation("Пушкинская");
    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("Площадь восстания"));
    expected.add(stationIndex.getStation("Владимирская"));
    expected.add(stationIndex.getStation("Пушкинская"));
    assertEquals(expected, routeCalculator.getShortestRoute(from, to));
  }
  public void testGetRouteWithOneConnection(){
    Station from = stationIndex.getStation("Василеостровская");
    Station to = stationIndex.getStation("Горьковская");
    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("Василеостровская"));
    expected.add(stationIndex.getStation("Гостиный двор"));
    expected.add(stationIndex.getStation("Невский проспект"));
    expected.add(stationIndex.getStation("Горьковская"));
    assertEquals(expected, routeCalculator.getShortestRoute(from, to));
  }
  public void testGetRouteWithTwoConnections(){
    Station from = stationIndex.getStation("Василеостровская");
    Station to = stationIndex.getStation("Обводный канал");
    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("Василеостровская"));
    expected.add(stationIndex.getStation("Гостиный двор"));
    expected.add(stationIndex.getStation("Маяковская"));
    expected.add(stationIndex.getStation("Площадь восстания"));
    expected.add(stationIndex.getStation("Владимирская"));
    expected.add(stationIndex.getStation("Пушкинская"));
    expected.add(stationIndex.getStation("Звенигородская"));
    expected.add(stationIndex.getStation("Обводный канал"));
    assertEquals(expected, routeCalculator.getShortestRoute(from, to));
  }
  public void testIsConnected(){
    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("Пушкинская"));
    assertEquals(expected, stationIndex.getConnectedStations(stationIndex.getStation("Звенигородская")));
  }
  public void testGetRouteViaConnectedLine(){
    Station from = stationIndex.getStation("Владимирская");
    Station to = stationIndex.getStation("Обводный канал");
    List<Station> expected = new ArrayList<>();
    expected.add(stationIndex.getStation("Владимирская"));
    expected.add(stationIndex.getStation("Пушкинская"));
    expected.add(stationIndex.getStation("Звенигородская"));
    expected.add(stationIndex.getStation("Обводный канал"));
    assertEquals(expected, routeCalculator.getShortestRoute(from, to));

  }



}
