public class Game
{
	public Character character;
	public Map map;

	public Game(ArrayList characterData)
	{
		character = new Character(characterData);
		map = new Map();
	}

	public Character getCharacter()
	{
		return character;
	}

	public Map getMap()
	{
		return map;
	}
}
