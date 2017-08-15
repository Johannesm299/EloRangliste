package matchmaking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import player.Player;
import player.PlayerStats;

/**
 * Match ist die Klasse in der zwei Teams gegeneinander antreten. Es werden
 * solange Runden gespielt, bis ein Team 16 Runden gewonnen hat, oder 30 Runden
 * gespielt wurden.
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 * 
 */
public class Match implements Runnable {

	private static int iDcounter = 0;

	private final int iD;
	private double gameRank;
	private Player[] teamOne = new Player[5];
	private double averageRatingTone;
	private Player[] teamTwo = new Player[5];
	private double averageRatingTtwo;
	private int numPlayersTone = 0;
	private int numPlayersTtwo = 0;

	// doof das so zu lösen, aber mal schauen. hat von 0-4 team 1 und von 5-9 team 2
	PlayerStats[] stats = new PlayerStats[10]; // aus Einfachheitsgründen package protected(für Round).

	private boolean gameNotStarted = true;
	private boolean gameInProgress = false;
	private boolean gameFinished = false;

	private int roundsT1 = 0;
	private int roundsT2 = 0;
	private Round[] r = new Round[30];
	private int playerSkill[] = new int[10];

	private int winner;

	private PrintWriter writer;

	/**
	 * Konstruktor der Klasse Match.
	 * 
	 * @param p
	 *            initialer Spieler, der den anfaenglichen gameRank des Matches
	 *            bestimmt.
	 */
	public Match(Player p, File dir) throws FileNotFoundException, UnsupportedEncodingException {
		teamOne[0] = p;
		stats[0] = new PlayerStats(teamOne[0], this);
		playerSkill[0] = p.getThisGamesSkill();
		numPlayersTone++;
		gameRank = p.getRank().getEloNumber();
		averageRatingTone = p.getRank().getEloNumber();
		// System.out.println(p.getName()+" to Team 1(konstruktor)");
		// System.out.println(averageRatingTone);
		// System.out.println(averageRatingTtwo);
		iD = iDcounter++;
		File wr = new File(dir, "Match " + iD);
		writer = new PrintWriter(wr);
	}

	/**
	 * Fuegt einen Spieler diesem Match hinzu.
	 * 
	 * @param p
	 *            der zu hinzufügende Spieler
	 */
	public void addPlayer(Player p) {
		if (averageRatingTone >= averageRatingTtwo) {
			if (numPlayersTtwo != 5) {
				teamTwo[numPlayersTtwo] = p;
				stats[5 + numPlayersTtwo] = new PlayerStats(teamTwo[numPlayersTtwo], this);
				playerSkill[5 + numPlayersTtwo] = p.getThisGamesSkill();
				averageRatingTtwo = (((averageRatingTtwo * numPlayersTtwo) + p.getRank().getEloNumber() + 0.0)
						/ (numPlayersTtwo + 1.0));
				numPlayersTtwo++;
				gameRank = (averageRatingTone * numPlayersTone + averageRatingTtwo * numPlayersTtwo + 0.0)
						/ (numPlayersTone + numPlayersTtwo + 0.0);
			} else if (numPlayersTtwo == 5) {
				teamOne[numPlayersTone] = p;
				stats[numPlayersTone] = new PlayerStats(teamOne[numPlayersTone], this);
				playerSkill[numPlayersTone] = p.getThisGamesSkill();
				averageRatingTone = (((averageRatingTone * numPlayersTone) + p.getRank().getEloNumber() + 0.0)
						/ (numPlayersTone + 1.0));
				numPlayersTone++;
				gameRank = (averageRatingTone * numPlayersTone + averageRatingTtwo * numPlayersTtwo + 0.0)
						/ (numPlayersTone + numPlayersTtwo + 0.0);
			}
		} else if (averageRatingTone < averageRatingTtwo) {
			if (numPlayersTone != 5) {
				teamOne[numPlayersTone] = p;
				stats[numPlayersTone] = new PlayerStats(teamOne[numPlayersTone], this);
				playerSkill[numPlayersTone] = p.getThisGamesSkill();
				averageRatingTone = (((averageRatingTone * numPlayersTone) + p.getRank().getEloNumber() + 0.0)
						/ (numPlayersTone + 1.0));
				numPlayersTone++;
				gameRank = (averageRatingTone * numPlayersTone + averageRatingTtwo * numPlayersTtwo + 0.0)
						/ (numPlayersTone + numPlayersTtwo + 0.0);
			} else if (numPlayersTone == 5) {
				teamTwo[numPlayersTtwo] = p;
				stats[5 + numPlayersTtwo] = new PlayerStats(teamTwo[numPlayersTtwo], this);
				playerSkill[5 + numPlayersTtwo] = p.getThisGamesSkill();
				averageRatingTtwo = (((averageRatingTtwo * numPlayersTtwo) + p.getRank().getEloNumber() + 0.0)
						/ (numPlayersTtwo + 1.0));
				numPlayersTtwo++;
				gameRank = (averageRatingTone * numPlayersTone + averageRatingTtwo * numPlayersTtwo + 0.0)
						/ (numPlayersTone + numPlayersTtwo + 0.0);
			}
		}
		if (numPlayersTone > 5 || numPlayersTtwo > 5) {
			System.out.println("Diese Meldung sollte niemals erscheinen // zu viele Spieler in einem Team");
		}

	}

	/**
	 * Gibt die ID dieses Spieles zurück
	 * 
	 * @return die ID dieses Spieles
	 */
	public int getId() {
		return iD;
	}

	/**
	 * Gibt den gameRank dieses Matches zurück
	 * 
	 * @return gameRank dieses Matches
	 */
	public double getGameRank() {
		return gameRank;
	}

	/**
	 * Gibt zurueck, ob dieses Match nicht nicht gestartet hat.
	 * 
	 * @return true wenn das Match noch nicht gestartet hat. Ansonsten false
	 */
	public boolean isGameNotStarted() {
		return gameNotStarted;
	}

	/**
	 * Gibt zurück, ob das Match bereits gestartet hat und noch nicht beendet ist.
	 * 
	 * @return true, wenn das Match bereits gestartet hat und noch nicht beendet
	 *         ist. Ansonsten false
	 */
	public boolean isGameInProgress() {
		return gameInProgress;
	}

	/**
	 * Gibt an, ob das Match bereits beendet ist.
	 * 
	 * @return true, wenn das Match beendet ist. Ansonsten false
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	/**
	 * Gibt den echten Skill des Spieler an dem jeweiligen Index zurück.
	 * 
	 * @param index
	 *            der Index des Spielers
	 * @return der echte Skill des Spielers
	 */
	public int getPlayerSkill(int index) {
		return playerSkill[index];
	}

	/**
	 * Gibt die Runde zurück in dem sich das Match derzeit befindet.
	 * 
	 * @return Summe von Team1 gewonnen Runden und Team2 gewonnenen Runden
	 */
	public int getCurrentRound() {
		return roundsT1 + roundsT2;
	}

	/**
	 * Gibt das Gewinnerteam dieses Matches zurück.
	 * 
	 * @return Gewinnerteam dieses Matches
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * Gibt den Writer zurück, der zur Dokumentation des Matches verantwortlich ist.
	 * 
	 * @return Writer, der zur Dokumentation des Matches verantwortlich ist
	 */
	public PrintWriter getWriter() {
		return writer;
	}

	/**
	 * Gibt zurück, ob das Match voll ist, oder nicht.
	 * 
	 * @return true, wenn die Anzahl der Spieler in diesem Match =10 ist. Ansonsten
	 *         false.
	 */
	public boolean matchIsFull() {
		return numPlayersTone + numPlayersTtwo == 10;
	}

	/**
	 * Gibt ein Array von Spielern in diesem Match zurück.
	 * 
	 * @return Array von Spielern in diesem Match
	 */
	public Player[] getPlayers() {
		Player[] p = new Player[numPlayersTone + numPlayersTtwo];
		for (int i = 0; i < numPlayersTone; i++) {
			p[i] = teamOne[i];
		}
		for (int i = 0; i < numPlayersTtwo; i++) {
			p[i + numPlayersTone] = teamTwo[i];
		}
		return p;
	}

	/**
	 * Schreibt die Spieler, sowie deren Daten, beider Teams in das Textdokument
	 * fuer dieses Match.
	 */
	public void printTeams() {
		writer.println("//Team 1: ");
		// System.out.println("//Team 1: ");
		for (int i = 0; i < teamOne.length; i++) {
			// System.out.println(teamOne[i].getName() + " S: " +
			// teamOne[i].getPlayerSkill() + " v: " + teamOne[i].getPlayerVolatility() + "
			// RS: " + playerSkill[i]);
			writer.println(teamOne[i].getName() + " S: " + teamOne[i].getPlayerSkill() + " v: "
					+ teamOne[i].getPlayerVolatility() + " RS: " + playerSkill[i]);
		}
		writer.println("///////////////////////");
		writer.println("//Team 2: ");
		// System.out.println("///////////////////////");
		// System.out.println("//Team 2: ");
		for (int i = 0; i < teamTwo.length; i++) {
			// System.out.println(teamTwo[i].getName() + " S: " +
			// teamTwo[i].getPlayerSkill() + " v: " + teamTwo[i].getPlayerVolatility() + "
			// RS: " + playerSkill[i+5]);
			writer.println(teamTwo[i].getName() + " S: " + teamTwo[i].getPlayerSkill() + " v: "
					+ teamTwo[i].getPlayerVolatility() + " RS: " + playerSkill[i + 5]);
		}
		writer.println("Team 1 average Rating: " + averageRatingTone);
		writer.println("Team 2 average Rating: " + averageRatingTtwo);
		writer.println("///////////////////////");
		// System.out.println("///////////////////////");
	}

	/**
	 * Laesst beide Teams solange gegeneinander Spielen, bis eins 16 Runden gewonnen
	 * hat, oder 30 Runden gespielt wurden. Die gespielten Runden werden
	 * dokumentiert. Nach dem Spiel werden die Raenge der Spieler angepasst, sowie
	 * die Rangveraenderung dokumentiert. Anschließend betreten die Spieler dieses
	 * Spiels wieder die Warteschlange/Matchmaker.
	 */
	public void run() {
		printTeams();
		gameNotStarted = false;
		gameInProgress = true;
		for (int i = 0; i < r.length; i++) {
			r[i] = new Round(teamOne, teamTwo, this);
		}

		for (int i = 0; i < r.length; i++) {
			int k = r[i].playRound();
			if (k == 1) {
				roundsT1++;
				String vl[] = r[i].getRundenverlauf();
				for (int j = 0; j < vl.length; j++) {
					writer.println(vl[j]);
				}

				writer.println(
						"Team1 won Round" + getCurrentRound() + " Es steht Team1: " + roundsT1 + " Team2: " + roundsT2);
				// System.out.println("Team1 won Round" + getCurrentRound() + " Es steht Team1:
				// " + roundsT1 + " Team2: " + roundsT2);
			} else if (k == 2) {
				roundsT2++;
				String vl[] = r[i].getRundenverlauf();
				for (int j = 0; j < vl.length; j++) {
					writer.println(vl[j]);
				}
				writer.println(
						"Team2 won Round" + getCurrentRound() + " Es steht Team1: " + roundsT1 + " Team2: " + roundsT2);
				// System.out.println("Team2 won Round" + getCurrentRound()+ " Es steht Team1: "
				// + roundsT1 + " Team2: " + roundsT2);
			} else if (k == 25) {
				System.out.println("Doof gelaufen!");
			}
			if (roundsT1 > 15) {
				winner = 1;
				writer.println("Team 1 hat gewonnen.");
				// System.out.println("Team 1 hat gewonnen.");
				break;
			}
			if (roundsT2 > 15) {
				winner = 2;
				writer.println("Team 2 hat gewonnen.");
				// System.out.println("Team 2 hat gewonnen.");
				break;
			}
			if (roundsT1 == 15 && roundsT2 == 15) {
				winner = 3;
				writer.println("Es ist unentschieden.");
				// System.out.println("Es ist unentschieden.");
				break;
			}

		}
		gameInProgress = false;
		gameFinished = true;
		for (int i = 0; i < stats.length; i++) {
			writer.println(stats[i].getStats());
			// stats[i].print();
		}
		int sumPT1 = 0;
		int sumPT2 = 0;
		for (int l = 0; l < 5; l++) {
			sumPT1 = sumPT1 + stats[l].getKills() * 2;
		}
		for (int l = 5; l < 10; l++) {
			sumPT2 = sumPT2 + stats[l].getKills() * 2;
		}
		writer.println("Elopunkte vor dem Spiel");
		writer.println("Team1: ");
		for (int y = 0; y < teamOne.length; y++) {
			writer.println(teamOne[y].getName() + ": " + teamOne[y].getRank().getEloNumber());
		}
		writer.println("Team2: ");
		for (int y = 0; y < teamTwo.length; y++) {
			writer.println(teamTwo[y].getName() + ": " + teamTwo[y].getRank().getEloNumber());
		}
		double sumRangpunkteT1 = 0;
		double sumRangpunkteT2 = 0;
		for (int k = 0; k < 5; k++) {
			sumRangpunkteT1 = sumRangpunkteT1 + teamOne[k].getRank().getEloNumber();
			sumRangpunkteT2 = sumRangpunkteT2 + teamTwo[k].getRank().getEloNumber();
		}
		for (int j = 0; j < 5; j++) {
			stats[j].getPlayer().getRank().adjustRanking(sumRangpunkteT1, sumRangpunkteT2, 1, winner, roundsT1,
					roundsT2, stats[j].getKills() * 2, sumPT1);
			if (winner == 1) {
				stats[j].getPlayer().increaseWonGames();
				stats[j].getPlayer().increaseGames();
			} else {
				stats[j].getPlayer().increaseGames();
			}
		}
		for (int j = 5; j < 10; j++) {
			stats[j].getPlayer().getRank().adjustRanking(sumRangpunkteT1, sumRangpunkteT2, 2, winner, roundsT1,
					roundsT2, stats[j].getKills() * 2, sumPT2);
			if (winner == 2) {
				stats[j].getPlayer().increaseWonGames();
				stats[j].getPlayer().increaseGames();
			} else {
				stats[j].getPlayer().increaseGames();
			}
		}
		writer.println("Elopunkte nach dem Spiel");
		writer.println("Team1: ");
		for (int y = 0; y < teamOne.length; y++) {
			writer.println(teamOne[y].getName() + ": " + teamOne[y].getRank().getEloNumber());
		}
		writer.println("Team2: ");
		for (int y = 0; y < teamTwo.length; y++) {
			writer.println(teamTwo[y].getName() + ": " + teamTwo[y].getRank().getEloNumber());
		}
		writer.close();
		for (int i = 0; i < 10; i++) {
			Matchmaker.enterQueue(stats[i].getPlayer());
			System.out.println(stats[i].getPlayer() + " betritt die q! nach match " + iD);
		}
	}
}
