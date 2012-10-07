public class Character {

	ArrayList<Object> characterData;
	Planet planet;
	Ship ship;
	Contract contract;

	public Character (ArrayList<Object> characterData) {
		this.characterData = characterData;
	}
	
	public void setPlanet (Planet planet) {
		this.planet = planet;
	}
	
	public Planet getPlanet () {
		return planet;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public Ship getShip () { 
		return ship;
	}
	
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	public ArrayList<Object> getCharacterData() {
		return characterData;
	}
	
	public void setCharacterData(ArrayList<Object> characterData) {
		this.characterData = characterData;
	}
}
		