package com.cs2340.spacetrader;

import java.io.Serializable;

public class SaveState implements Serializable {
	private Map map;
	private Player player;
	
	public SaveState(Player player, Map map) {
		this.map = map;
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
