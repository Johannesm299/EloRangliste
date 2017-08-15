package Random;

import java.util.HashMap;

/**
 * FightingRandomizer ist für die zufällige Auswahl der Spieler verantwortlich,
 * die während einer Runde gegeneinander Kämpfen
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */

public class FightingRandomizer {

	HashMap<Integer, Integer> random;

	/**
	 * Konstruktor fuer die Klasse FightingRandomizer.
	 * 
	 * @param h
	 *            HashMap der noch lebenden Spieler mit dem Spielerindex als Value
	 */
	public FightingRandomizer(HashMap<Integer, Integer> h) {
		random = h;
	}

	/**
	 * Methode die einen zufällig ausgewählten Spieler zurückgibt.
	 * 
	 * @return zufälliger Value von random
	 */
	public int getRandomPlayerIndex() {

		Object[] t = random.keySet().toArray();

		int r = (int) (Math.random() * t.length);

		return random.get(r);
	}

}
