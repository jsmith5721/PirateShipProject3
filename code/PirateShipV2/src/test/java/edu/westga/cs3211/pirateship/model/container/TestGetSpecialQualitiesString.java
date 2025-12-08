package edu.westga.cs3211.pirateship.model.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.Container;
import edu.westga.cs3211.pirateship.model.SpecialQualities;

public class TestGetSpecialQualitiesString {
	
	private Container container;
	private Collection<SpecialQualities> qualities;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.qualities = new ArrayList<>();
	}
	
	@Test
	public void testGetSpecialQualitiesStringEmpty() {
		String expected = "";
		container = new Container(100, qualities);
		String actual = this.container.getSpecialQualitiesString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetSpecialQualitiesStringOneQuality() {
		qualities.add(SpecialQualities.EXPLOSIVE);
		container = new Container(100, qualities);
		String expected = "EXPLOSIVE,";
		String actual = this.container.getSpecialQualitiesString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetSpecialQualitiesString() {
		qualities.add(SpecialQualities.EXPLOSIVE);
		qualities.add(SpecialQualities.FRAGILE);
		container = new Container(100, qualities);
		String expected = "EXPLOSIVE, FRAGILE,";
		String actual = this.container.getSpecialQualitiesString();
		assertEquals(expected, actual);
	}
}
