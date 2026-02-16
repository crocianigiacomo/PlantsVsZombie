package tp1.p2.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Sun;
import tp1.p2.view.Messages;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}
	public String positionToString(int col, int row) {
		StringBuilder buffer = new StringBuilder();
		boolean sunPainted = false;
		boolean sunAboutToPaint = false;

		for (GameObject g : gameObjects) {
			if(g.isAlive() && g.getCol() == col && g.getRow() == row) {
				String objectText = g.toString();
				sunAboutToPaint = objectText.indexOf(Messages.SUN_SYMBOL) >= 0;
				if (sunAboutToPaint) {
					if (!sunPainted) {
						buffer.append(objectText);
						sunPainted = true;
					}
				} else 
					buffer.append(objectText);
			}
		}

		return buffer.toString();
	}
	public void clear() {
		for (GameObject shattered : gameObjects)
			shattered.onExit();
		gameObjects.clear();
	}
	public void add(GameObject object) {
		object.onEnter();
		gameObjects.add(object);
	}
	public boolean removeDead() {
		ArrayList<GameObject> removeds = new ArrayList<GameObject>();
		for (GameObject gameObject : gameObjects) {
			if(!gameObject.isAlive()) {
				gameObject.onExit();
				removeds.add(gameObject);
			}
		}
		gameObjects.removeAll(removeds);
		return false;
	}
	public GameItem getGameItemInPosition(int col, int row) {
		int i = 0;
		boolean found = false;
		GameObject retorno = null; //Caso por defecto: Lista vacia
		
		while(i < gameObjects.size() && !found) {
			retorno = gameObjects.get(i);
			
			if(retorno.isInPosition(col, row))
				found = true;
			else {
				retorno = null;
				++i;
			}
		}
		
		return retorno;
	}
	public List<GameItem> getGameItemsInPosition(int col, int row){
		List<GameItem> retorno = new ArrayList<GameItem>();
		GameObject aux = null;
		
		for(int i = 0; i < gameObjects.size(); ++i) {
			aux = gameObjects.get(i);
			
			if(aux.isInPosition(col, row))
				retorno.add(aux);
		}
		
		return Collections.unmodifiableList(retorno);
	}
	public void update() {
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject g = gameObjects.get(i);
			
			if(g.isAlive()) 
				g.update();	
		}
	}
	public boolean isFullyOccupied(int col, int row) {
		int i=0;
		boolean fullyOcuppied = false;

		while (i<gameObjects.size() && !fullyOcuppied) {
			GameObject g = gameObjects.get(i);
			
			if (g.isAlive() && g.isInPosition(col, row))
				fullyOcuppied = g.fillPosition();
			++i;
		}

		return fullyOcuppied;
	}
	public boolean isObjectInPosition (int col, int row) {
		int i = 0;
		boolean found = false;
		
		if(gameObjects.size() > 0) {
			while (i < gameObjects.size() && !found) {
				if (gameObjects.get(i).isAlive()
					&&	gameObjects.get(i).isInPosition(col, row)
					&& gameObjects.get(i).fillPosition())
					found = true;
				else ++i;
			}
		}
		
		return found;
	}
	public boolean catchObject(int col, int row) {
		List<GameItem> coins = getGameItemsInPosition(col, row);
		if (coins.isEmpty())
			return false;
		else {
			boolean coin = false;
			for (int i = 0; i < coins.size(); i++) {
				if (coins.get(i).catchObject())
					coin = true;
			}
			return coin;
		}
	}
}