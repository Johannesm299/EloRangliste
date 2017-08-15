package player;

import matchmaking.Match;

/**
 * PlayerStats speichert die Tötungen und Tode eines Spielers während eines
 * Matches.
 * 
 * @author Johannes Schürmeyer
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
	 * Erhöht die Tötungen des Spielers.
	 */
	public void increaseKills() {
		kills++;
	}

	/**
	 * Erhöht die Tode des Spielers.
	 */
	public void increaseDeaths() {
		deaths++;
	}

	/**
	 * Gibt den Spieler dieser Statistiken zurück
	 * 
	 * @return Spieler dieser Statistiken
	 */
	public Player getPlayer() {
		return p;
	}

	/**
	 * Gibt das Match dieser Statistiken zurück.
	 * 
	 * @return Match dieser Statistiken
	 */
	public Match getMatch() {
		return m;
	}

	/**
	 * Druckt den Namen des Spielers, sowie seine Tötungen und Tode aus.
	 */
	public void print() {
		if (m.isGameFinished() == true) {
			System.out.println(p.getName() + " Kills: " + kills + " Deaths: " + deaths);
		}

	}

	/**
	 * Gibt einen String mit dem Namen des Spielers, sowie seinen Tötungen und Toden
	 * zurück.
	 * 
	 * @return String mit dem Namen des Spielers, sowie seinen Tötungen und Toden
	 */
	public String getStats() {
		return p.getName() + " Kills: " + kills + " Deaths: " + deaths;
	}

	/**
	 * Gibt die Tötungen des Spielers zurück
	 * 
	 * @return Tötungen des Spielers
	 */
	public int getKills() {
		return kills;
	}

}
