package matchmaking;

import java.util.HashMap;
import java.util.Iterator;

import Random.FightingRandomizer;
import player.Player;

/**
 * Die Klasse Round lässt zwei Teams mit jeweils fünf Spielern gegeneinander
 * Kämpfen, bis ein Team gewonnen hat.
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */
public class Round {

	private final Match myMatch;
	private HashMap<Integer, Player> t1 = new HashMap<Integer, Player>();
	private HashMap<Player, Integer> t11 = new HashMap<Player, Integer>();
	private HashMap<Integer, Player> t2 = new HashMap<Integer, Player>();
	private HashMap<Player, Integer> t21 = new HashMap<Player, Integer>();
	private String r1 = "";
	private String r2 = "";
	private String r3 = "";
	private boolean roundPlayed = false;

	/**
	 * Der Konstruktor der Klasse Round. Fuer den Ablauf der Runde werden die
	 * Spieler des jeweiligen Teams in zwei HashMaps gespeichert. Die eine hat den
	 * Spielerindex als Schlüssel und den Spieler als Value und umgekehrt.
	 * 
	 * @param t1
	 *            Array von Playern, die Team 1 sind
	 * @param t2
	 *            Array von Playern, die Team 2 sind
	 * @param m
	 *            Match in dem sich diese Round befindet.
	 */
	public Round(Player[] t1, Player[] t2, Match m) {
		for (int i = 0; i < t1.length; i++) {
			this.t1.put(i, t1[i]);
			t11.put(t1[i], i);
		}
		for (int i = 0; i < t2.length; i++) {
			this.t2.put(i, t2[i]);
			t21.put(t2[i], i);
		}
		myMatch = m;

	}

	/**
	 * Lässt zwei Spieler gegeneinander Kämpfen und entfernt den Verlierer aus
	 * beiden HashMaps.
	 * 
	 * @param p1
	 *            Spieler der Kaempft
	 * @param p2
	 *            anderer Spieler der Kaempft
	 */
	private void fight(Player p1, Player p2) {
		int p1Skill = myMatch.getPlayerSkill(t11.get(p1));
		int p2Skill = myMatch.getPlayerSkill(t21.get(p2) + 5);

		int randomNumber = (int) (Math.random() * 100);
		int w1 = 50 + ((p1Skill - p2Skill) / 2);
		int w2 = 51 + ((p1Skill - p2Skill) / 2);
		// System.out.println("w1 :" + p1.getName() + " " + p1Skill + " " + p2.getName()
		// + " " + p2Skill);
		if (randomNumber <= w1) {
			myMatch.stats[t11.get(p1)].increaseKills();
			myMatch.stats[t21.get(p2) + 5].increaseDeaths();
			r1 = randomNumber + " wurde gewürfelt, " + p1.getName() + " hat 1 bis " + (50 + (p1Skill - p2Skill) / 2)
					+ ", deshalb gewinnt er.";
			r2 = p2.getName() + " hat " + (1 + (50 + (p1Skill - p2Skill) / 2)) + " bis 99, deshalb verliert er.";
			r3 = t1.get(t11.get(p1)).getName() + " killed " + t2.get(t21.get(p2)).getName();
			// System.out.println(randomNumber + " wurde gewürfelt, " + p1.getName() + " hat
			// 1 bis " + (50+(p1Skill-p2Skill)/2) + ", deshalb gewinnt er.");
			// System.out.println(p2.getName() + " hat " + (1+(50+(p1Skill-p2Skill)/2)) + "
			// bis 99, deshalb verliert er.");
			// System.out.println(t1.get(t11.get(p1)).getName() + " killed " +
			// t2.get(t21.get(p2)).getName());
			t2.remove(t21.remove(p2));

		} else if (randomNumber >= w2) {
			myMatch.stats[t11.get(p1)].increaseDeaths();
			myMatch.stats[t21.get(p2) + 5].increaseKills();
			r1 = randomNumber + " wurde gewürfelt, " + p2.getName() + " hat " + (1 + (50 + (p1Skill - p2Skill) / 2))
					+ " bis 99, deshalb gewinnt er.";
			r2 = p1.getName() + " hat 1 bis " + (50 + (p1Skill - p2Skill) / 2) + ", deshalb verliert er.";
			r3 = t2.get(t21.get(p2)).getName() + " killed " + t1.get(t11.get(p1)).getName();
			// System.out.println(randomNumber + " wurde gewürfelt, " + p2.getName() + " hat
			// " + (1+(50+(p1Skill-p2Skill)/2)) + " bis 99, deshalb gewinnt er.");
			// System.out.println(p1.getName() + " hat 1 bis " + (50+(p1Skill-p2Skill)/2) +
			// ", deshalb verliert er.");
			// System.out.println(t2.get(t21.get(p2)).getName() + " killed " +
			// t1.get(t11.get(p1)).getName());
			t1.remove(t11.remove(p1));

		} else {
			System.out.println("fight funktioniert nicht richtig");
		}

	}

	/**
	 * Gibt den Rundenverlauf zurück. Dieser Besteht aus aus dem bei jedem Kampf
	 * gewuerfeltem Wert und den Intervallen beider Spieler. Außerdem wird angegeben
	 * welcher Spieler gewinnt und verliert.
	 * 
	 * @return der Rundenverlauf
	 */
	public String[] getRundenverlauf() {
		String[] r = new String[3];
		r[0] = r1;
		r[1] = r2;
		r[2] = r3;
		return r;
	}

	/**
	 * Lässt solange Spieler aus beiden Teams gegeneinander Kämpfen, bis alle
	 * Spieler aus einem Team getöted wurden.
	 * 
	 * @return Gewinnerteam dieser Runde
	 */
	public int playRound() {
		while (!t1.isEmpty() && !t2.isEmpty()) {

			Iterator<Integer> one = t1.keySet().iterator();
			Iterator<Integer> two = t2.keySet().iterator();
			HashMap<Integer, Integer> h1 = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> h2 = new HashMap<Integer, Integer>();

			for (int i = 0; i < t1.size(); i++) {
				h1.put(i, (int) one.next());
			}
			for (int i = 0; i < t2.size(); i++) {
				h2.put(i, (int) two.next());
			}
			fight(t1.get(new FightingRandomizer(h1).getRandomPlayerIndex()),
					t2.get(new FightingRandomizer(h2).getRandomPlayerIndex()));

		}
		if (t1.isEmpty()) {
			roundPlayed = true;
			return 2;
		} else if (t2.isEmpty()) {
			roundPlayed = true;
			return 1;
		} else {
			System.out.println("Rundenberechnung Schiefgelaufen!");
			return 25;
		}
	}
}
