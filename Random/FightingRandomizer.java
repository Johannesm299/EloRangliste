package Random;

import java.util.HashMap;

/**
 * FightingRandomizer ist f�r die zuf�llige Auswahl der Spieler verantwortlich,
 * die w�hrend einer Runde gegeneinander K�mpfen
 * 
 * @author Johannes Sch�rmeyer
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
	 * Methode die einen zuf�llig ausgew�hlten Spieler zur�ckgibt.
	 * 
	 * @return zuf�lliger Value von random
	 */
	public int getRandomPlayerIndex() {

		Object[] t = random.keySet().toArray();

		int r = (int) (Math.random() * t.length);

		return random.get(r);
	}

}
