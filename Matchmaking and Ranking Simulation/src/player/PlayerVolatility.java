package player;

/**
 * PlayerVolatility ist das Intervall zwischen dem der echte Skill des Spielers
 * pro Spiel schwanken kann. Das Intervall ist prozentual, d.h. 1 ist der echte
 * Skill des Spielers und die Volatility beschreibt wie groß die Abweichung von
 * 1 aus sein kann.
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */
public class PlayerVolatility {

	private final double range;

	/**
	 * Konstruktor fuer die Klasse PlayerVolatility.
	 * 
	 * @param range
	 *            die prozentuale größtmögliche Abweichung des echten Skill des
	 *            Spielers
	 */
	public PlayerVolatility(double range) {
		if (range <= 0.5)
			this.range = range;
		else
			throw new RuntimeException("Volatility too extreme");

	}

	/**
	 * Methode um den Skill des Spielers für dieses Match zurückzubekommen.
	 * 
	 * @return der Skill den der Spieler in diesem Match hat
	 */
	public double getThisGamesSkillFactor() {
		return ((1.0 - range) + Math.random() * ((1.0 + range) - (1.0 - range)));
	}

	/**
	 * Getter fuer das Schwankungsintervall
	 * 
	 * @return prozentuales Schwankungsintervall
	 */
	double getVolatility() { // package protected
		return range;
	}

}
