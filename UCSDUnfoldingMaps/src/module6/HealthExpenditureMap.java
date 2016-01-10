package module6;

import processing.core.PApplet;
import processing.core.PConstants;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.*;

import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;

import de.fhpotsdam.unfolding.marker.Marker;

public class HealthExpenditureMap extends PApplet {

	UnfoldingMap map;
	List<Feature> countries;
	List<Marker> countryMarkers;
	Map<String, Map<Integer, Double>> expenditureMap;

	Marker lastSelected;

	String mapDisplay;

	// flag to see if it's the map is just loaded
	boolean isBootup = true;
	// global var for the currently selected year
	int currentYear = -99;

	final int menuX = 200;
	final int menuY = 50;

	final int spend20 = color(255, 204, 204);
	final int spend30 = color(255, 153, 153);
	final int spend40 = color(255, 102, 102);

	final int spend50 = color(255, 51, 51);
	final int spend60 = color(255, 0, 0);
	final int spend70 = color(204, 0, 0);

	final int spend80 = color(153, 0, 0);
	final int spend90 = color(102, 0, 0);
	final int spend100 = color(51, 0, 0);
/*
	final int add0 = color(204, 204, 255);
	final int add10 = color(153, 153, 255);
	final int add20 = color(102, 102, 255);
	final int add30 = color(51, 51, 255);
	final int add40 = color(0, 0, 255);
	final int add50 = color(0, 0, 200);

	final int minus0 = color(204, 255, 204);
	final int minus10 = color(153, 255, 153);
	final int minus20 = color(102, 255, 102);
	final int minus30 = color(51, 255, 51);
	final int minus40 = color(0, 255, 0);
	final int minus50 = color(0, 204, 0);
*/
	public void setup() {
		size(1600, 900, OPENGL);

		map = new UnfoldingMap(this, 50, 50, 1366, 768,
				new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		expenditureMap = new HashMap<String, Map<Integer, Double>>();

		expenditureMap = ParseFeed.loadHealthExpenditureFromCSV(this,
				"health_expenditure.csv");

		// System.out.println(mpMap);

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		// map.addMarkers(countryMarkers);
		// System.out.println(countryMarkers.get(0).getId());

		// Country markers are shaded according to life expectancy (only once)
		// shadeCountries(2000);

		renderMenu(menuX, menuY, -99);

	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();

		if (this.mapDisplay != null) {
			text(this.mapDisplay, mouseX, mouseY);
		}

	}

	/**
	 * The event handler for mouse clicks mouseClicked event
	 */
	@Override
	public void mouseClicked() {

		int year = clickInterpreter(mouseX, mouseY);

		if (year != -99) {
			// check if the application is just opened
			if (isBootup) {
				map.addMarkers(countryMarkers);
				this.isBootup = false;
			}

			renderMenu(menuX, menuY, year);
			shadeCountries(year);
		}
	}

	/**
	 * Event handler that gets called automatically when the mouse moves.
	 */
	@Override
	public void mouseMoved() {
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;

		}
		selectMarkerIfHover(this.countryMarkers);
	}

	// If there is a marker selected
	private void selectMarkerIfHover(List<Marker> markers) {
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}

		for (Marker m : markers) {
			if (m.isInside(map, mouseX, mouseY)) {
				lastSelected = m;
				m.setSelected(true);

				String countryId = m.getId();

				System.out.println("year is " + this.currentYear);
				System.out.println("country is " + countryId);

				if (this.expenditureMap.containsKey(countryId)) {
					Map<Integer, Double> yearData = this.expenditureMap
							.get(countryId);
					if (yearData.containsKey(this.currentYear))
						System.out.println("data is "
								+ yearData.get(this.currentYear));
					this.mapDisplay = m.getProperty("name").toString() + ":  "
							+ yearData.get(this.currentYear).toString();
				} else {
					this.mapDisplay = m.getProperty("name").toString();
				}
				fill(0, 0, 255);
				textSize(15);
				text(this.mapDisplay, mouseX, mouseY);
				return;
			}
		}

		this.mapDisplay = null;
	}

	// helper method which uses mouse coordinates to determine menu item
	// selected
	private int clickInterpreter(int x, int y) {

		int year = -99;

		if (x >= 210 && x <= 250 && y >= 100 && y <= 475) {
			// System.out.println("within range");

			// y starts at 100, every item adds 20 more height
			year = (y - 100) / 20 + 1995;

			// System.out.println("It's year " + year);

		}

		this.currentYear = year;

		return year;
	}

	/** highlight selected menu */
	private void renderMenu(float x, float y, int selectedYear) {

		String title = "Select Cut";

		pushStyle();

		fill(255, 255, 255);
		textSize(15);
		rectMode(PConstants.CORNER);
		rect(x, y, 120, 700);
		fill(0, 0, 0);
		textAlign(PConstants.LEFT, PConstants.TOP);
		text(title, x + 5, y + 5);

		// making all menu items
		fill(0, 0, 255);
		textSize(15);

		float menuItemX = x + 10;
		float menuItemY = y + 50;

		for (int i = 1995; i <= 2013; i++) {
			if (i == selectedYear)
				fill(255, 0, 0);
			text("" + i, menuItemX, menuItemY);
			if (i == selectedYear)
				fill(0, 0, 255);
			menuItemY = menuItemY + 20;
		}
		/*
		if (selectedYear == 2014)
			fill(255, 0, 0);
		text("Trend", menuItemX, menuItemY);
		menuItemY = menuItemY + 20;
		textSize(12);
		text("(changes since ", menuItemX - 5, menuItemY);
		menuItemY = menuItemY + 20;
		text("1995)", menuItemX - 5, menuItemY);
		*/
		popStyle();
	}

	// Helper method to color each country based on health expenditure
	// Red-orange indicates low (near 40)
	// Blue indicates high (near 100)
	private void shadeCountries(int year) {

		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();

			if (this.expenditureMap.containsKey(countryId)) {
				Map<Integer, Double> yearData = this.expenditureMap
						.get(countryId);

				if (yearData.containsKey(year)) {

					Double data = yearData.get(year);

					if (data <= 20)
						marker.setColor(spend20);
					else if (data <= 30)
						marker.setColor(spend30);
					else if (data <= 40)
						marker.setColor(spend40);
					else if (data <= 50)
						marker.setColor(spend50);
					else if (data <= 60)
						marker.setColor(spend60);
					else if (data <= 70)
						marker.setColor(spend70);
					else if (data <= 80)
						marker.setColor(spend80);
					else if (data <= 90)
						marker.setColor(spend90);
					else
						marker.setColor(spend100);
				} else {
					marker.setColor(color(150, 150, 150));
				}

			}

			else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}

}
