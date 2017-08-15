package player;

import java.util.Random;

import ranking.Playerrating;

/**
 * Player ist die Klasse die Objekte unterschiedlicher Spieler erstellt. Ein
 * Teil der jeweiligen Spielerdaten werden lokal gespeicht, der Rest in anderen
 * Klassen.
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */

public class Player {

	private static int id = 0;
	private final int playerId;
	private final int playerSkill; // Wert zwischen 0 und 100
	private final PlayerVolatility vl; // Wie sehr die Performacen des Spielers schwankt
	private Playerrating rank; // die Skill-Group des Spielers
	@SuppressWarnings("unused")
	private boolean isSearching = false;
	@SuppressWarnings("unused")
	private boolean isPlaying = false;
	private int wonGames;
	private int games;
	private String name;

	/**
	 * Spezieller Konstruktor fuer die Klasse Player.
	 * 
	 * @param skill
	 *            der echte Skill den der Spieler bekommen soll
	 * @param range
	 *            das Schwankungsintervall des Spielers
	 * @param n
	 *            der Name des Spielers
	 * @param elo
	 *            die Elozahl des Spielers
	 */
	public Player(int skill, double range, String n, int elo) {
		playerId = id++;
		vl = new PlayerVolatility(range);
		name = n;
		rank = new Playerrating(elo, this);
		if (skill >= 0 && skill <= 100)
			playerSkill = skill;
		else
			throw new RuntimeException("Player Skill not between 0 and 100");

	}

	/**
	 * Default Konstruktor fuer die Klasse Player.
	 * 
	 * Der Name des Spielers wird anhand seiner ID bestimmt, das Schankungsintervall
	 * ist ein Zufallswert innerhalb [0,0,5), der Rang des SPielers wird
	 * default-mäßig bestimmt und sein echter Skill ist Element einer
	 * pseudo-Normalverteilung mit einem Mittelwert von 49 und einer
	 * Standartabweichung von 13.
	 */
	public Player() {
		playerId = id++;
		vl = new PlayerVolatility((Math.random() * 0.5));
		name = "Player " + playerId;
		Random r = new Random();
		playerSkill = (int) ((r.nextGaussian() * 13) + 50);
		rank = new Playerrating(this);
	}

	/**
	 * Setzt fest, dass der Player nach einem Match sucht.
	 */
	public void startSearching() {
		isSearching = true;
	}

	/**
	 * Setzt fest, dass der Player ein Match spielt. Setzt fest, dass der Player
	 * nicht mehr nach einem Match sucht.
	 */
	public void foundGame() {
		isSearching = false;
		isPlaying = true;
	}

	/**
	 * Setzt fest, dass der Player nicht in einem Match spielt.
	 */
	public void gameEnded() {
		isPlaying = false;
	}

	/**
	 * Gibt den echten Skill des Spielers zurück
	 * 
	 * @return der echte Skill des Spielers
	 */
	public int getPlayerSkill() {
		return playerSkill;
	}

	/**
	 * Getter fuer das Schwankungsintervall
	 * 
	 * @return prozentuales Schwankungsintervall
	 */
	public double getPlayerVolatility() {
		return this.vl.getVolatility();
	}

	/**
	 * Erhöht die Anzahl der gewonnenen Spiele.
	 */
	public void increaseWonGames() {
		wonGames++;
	}

	/**
	 * Erhöht die Anzahl der gespielten Spiele.
	 */
	public void increaseGames() {
		games++;
	}

	/**
	 * Gibt die Anzahl der gewonnenen Spiele zurück.
	 * 
	 * @return die Anzahl der gewonnenen Spiele
	 */
	public int getWonGames() {
		return wonGames;
	}

	/**
	 * Gibt die Anzahl der gespielten Spiele zurück.
	 * 
	 * @return Anzahl der gespielten Spiele
	 */
	public int getGames() {
		return games;
	}

	/**
	 * Gibt den Skill fuer dieses Match zurück
	 * 
	 * @return der Skill fuer dieses Match
	 */
	public int getThisGamesSkill() {
		return (int) (vl.getThisGamesSkillFactor() * playerSkill);
	}

	/**
	 * Gibt das Objekt des Spielerrangs dieses Spielers zurück
	 * 
	 * @return das Objekt des Spielerrangs dieses Spielers
	 */
	public Playerrating getRank() {
		return rank;
	}

	/**
	 * Gibt den Namen dieses Spielers zurück
	 * 
	 * @return der Name dieses Spielers
	 */
	public String getName() {
		return name;
	}

	/**
	 * Überschreibt die von Object geerbte toString() Methode.
	 * 
	 * @return der Name des Spielers, sowie sein echter Skill und sein
	 *         Schwankungsintervall
	 */
	public String toString() {
		return name + " S: " + playerSkill + " vl: " + this.vl.getVolatility();
	}

}
