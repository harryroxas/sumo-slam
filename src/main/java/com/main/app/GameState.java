package com.main.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameState{
	private Map players=new HashMap();

	public GameState(){}
	
	/**
	 * Update the game state. Called when player moves
	 * @param name
	 * @param player
	 */
	public void update(String name, User player){
		players.put(name,player);
	}
	
	public String toString(){
		String retval="";
		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			User player=(User)players.get(name);
			retval+=player.toString()+":";
		}
		return retval;
	}
	
	/**
	 * Returns the map
	 * @return
	 */
	public Map getPlayers(){
		return players;
	}
}
