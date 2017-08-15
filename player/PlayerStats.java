package player;

import matchmaking.Match;

/**
 * PlayerStats speichert die T�tungen und Tode eines Spielers w�hrend eines
 * Matches.
 * 
 * @author Johannes Sch�rmeyer
 * @version 1.0
 *
 */

public class PlayerStats {

	private Match m;
	private Player p;
	private int kills = 0;
	private int deaths = 0;

	/**
	 * 
	 * @param pl
	 *            der Spieler dessen Match-Statistiken gespeichert werden
	 * @param ma
	 *            das Match, welches der Spieler spielt
	 */
	public PlayerStats(Player pl, Match ma) {
		m = ma;
		p = pl;
	}

	/**
	 * Erh�ht die T�tungen des Spielers.
	 */
	public void increaseKills() {
		kills++;
	}

	/**
	 * Erh�ht die Tode des Spielers.
	 */
	public void increaseDeaths() {
		deaths++;
	}

	/**
	 * Gibt den Spieler dieser Statistiken zur�ck
	 * 
	 * @return Spieler dieser Statistiken
	 */
	public Player getPlayer() {
		return p;
	}

	/**
	 * Gibt das Match dieser Statistiken zur�ck.
	 * 
	 * @return Match dieser Statistiken
	 */
	public Match getMatch() {
		return m;
	}

	/**
	 * Druckt den Namen des Spielers, sowie seine T�tungen und Tode aus.
	 */
	public void print() {
		if (m.isGameFinished() == true) {
			System.out.println(p.getName() + " Kills: " + kills + " Deaths: " + deaths);
		}

	}

	/**
	 * Gibt einen String mit dem Namen des Spielers, sowie seinen T�tungen und Toden
	 * zur�ck.
	 * 
	 * @return String mit dem Namen des Spielers, sowie seinen T�tungen und Toden
	 */
	public String getStats() {
		return p.getName() + " Kills: " + kills + " Deaths: " + deaths;
	}

	/**
	 * Gibt die T�tungen des Spielers zur�ck
	 * 
	 * @return T�tungen des Spielers
	 */
	public int getKills() {
		return kills;
	}

}
