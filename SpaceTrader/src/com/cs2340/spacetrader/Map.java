package com.cs2340.spacetrader;

import java.io.Serializable;

public class Map implements Serializable{
	private Planet[] planetArray;
	
	public Map(){
		generatePlanetArray();
	}
	
	public void generatePlanetArray(){
		//randomly create array of planets here
	}
}