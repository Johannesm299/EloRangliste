import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import matchmaking.Match;
import matchmaking.Matchmaker;
import player.Player;

/**
 * Klasse zum Testen der einzelnen Bestandteile, sowie des Durchlaufs der
 * Simulation.
 * 
 * @author Johannes Schürmeyer version 1.0
 *
 */
public class Testestest {

	/**
	 * Hauptmethode
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		// Testcode um einzelne Matches zu testen.
		/*
		 * Match m = new Match(new Player()); for(int i=0; i<9;i++){ m.addPlayer(new
		 * Player()); } m.printTeams(); m.run();
		 */

		// Testcode fuer eine kleine Anzahl von Spielern zum Matchmaken.
		/*
		 * Matchmaker m = Matchmaker.getInstance(); m.clearPlayers();
		 * m.setIntervallOG(50); Player pl [] = new Player [11]; for(int i=0;
		 * i<pl.length; i++){ pl[i]=new Player();
		 * 
		 * } m.run();
		 */
		/*
		 * Simulation mit 1001 Spielern, die erst 4000 Spiele spielen und dann noch
		 * einmal 4000 Spiele spielen.
		 */
		Matchmaker mmSystem = Matchmaker.getInstance();
		mmSystem.clearPlayers();
		int iG1 = 1000;
		int iG2 = 2000;
		Player pl[] = new Player[101];
		for (int i = 0; i < pl.length; i++) {
			pl[i] = new Player();
		}
		File dir = new File("Results");
		dir.mkdir();
		mmSystem.setIntervallOG(iG1);
		spieleSpieleBis(iG1, mmSystem, pl, dir);
		mmSystem.setIntervallOG(iG2);
		spieleSpieleBis(iG2, mmSystem, pl, dir);

	}

	/**
	 * Dokumentiert die Daten aller Spieler nach einer bestimmten Anzahl von
	 * Spielen, sowie die durchschnittliche Abweichung von echtem Skill und Rang.
	 * Außerdem wird die Anzahl der Spieler innerhalb bestimmter Skill - und
	 * Rangintervalle angezeigt. Die Daten werden in .txt Dateien im workplace unter
	 * dem Namen "Spieler & Ränge -- AnzahlSpiele" gespeichert.
	 * 
	 * @param spiele
	 *            die Anzahl der Spiele nachdem dokumentiert werden soll
	 * @param pl
	 *            die Spieler deren Daten dokumentiert werden
	 */
	private static void printRaenge(int spiele, Player[] pl, File dir)
			throws FileNotFoundException, UnsupportedEncodingException {
		File writer = new File(dir, "Spieler & Ränge -- " + spiele);
		PrintWriter mywriter = new PrintWriter(writer, "UTF-8");
		double standartabweichung = 0;
		int i5 = 0;
		int i10 = 0;
		int i15 = 0;
		int i20 = 0;
		int i25 = 0;
		int i30 = 0;
		int i35 = 0;
		int i40 = 0;
		int i45 = 0;
		int i50 = 0;
		int i55 = 0;
		int i60 = 0;
		int i65 = 0;
		int i70 = 0;
		int i75 = 0;
		int i80 = 0;
		int i85 = 0;
		int i90 = 0;
		int i95 = 0;
		int i100 = 0;

		int i800 = 0;
		int i860 = 0;
		int i920 = 0;
		int i980 = 0;
		int i1040 = 0;
		int i1100 = 0;
		int i1160 = 0;
		int i1220 = 0;
		int i1280 = 0;
		int i1340 = 0;
		int i1400 = 0;
		int i1460 = 0;
		int i1520 = 0;
		int i1580 = 0;
		int i1640 = 0;
		int i1700 = 0;
		int i1760 = 0;
		int i1820 = 0;
		int i1880 = 0;
		int i1940 = 0;
		for (int i = 0; i < pl.length; i++) {

			int winquote = (int) ((pl[i].getWonGames() + 0.0) / (pl[i].getGames() + 0.0) * 100);
			mywriter.println(pl[i].getName() + " RS: " + pl[i].getPlayerSkill() + " Rang: "
					+ pl[i].getRank().getEloNumber() + " gespielte Spiele: " + pl[i].getGames() + " davon Gewonnen: "
					+ pl[i].getWonGames() + " winquote(in%): " + winquote);
			int skillTop = 0;
			for (int j = 0; j < pl.length; j++) {
				if (pl[i].getPlayerSkill() >= pl[j].getPlayerSkill())
					skillTop++;
			}
			int skillProzent = (int) (((skillTop + 0.0) / (pl.length + 0.0)) * 100);
			int rangTop = 0;
			for (int j = 0; j < pl.length; j++) {
				if (pl[i].getRank().getEloNumber() >= pl[j].getRank().getEloNumber())
					rangTop++;
			}
			int rangProzent = (int) (((rangTop + 0.0) / (pl.length + 0.0)) * 100);
			standartabweichung = standartabweichung + Math.abs(skillProzent - rangProzent);
			mywriter.println("Skill befindet sich in den Top " + skillProzent + "%. Rang befindet sich in den Top "
					+ rangProzent + "%.");

			if (pl[i].getPlayerSkill() <= 5)
				i5 = i5 + 1;
			else if (pl[i].getPlayerSkill() <= 10)
				i10 = i10 + 1;
			else if (pl[i].getPlayerSkill() <= 15)
				i15 = i15 + 1;
			else if (pl[i].getPlayerSkill() <= 20)
				i20 = i20 + 1;
			else if (pl[i].getPlayerSkill() <= 25)
				i25 = i25 + 1;
			else if (pl[i].getPlayerSkill() <= 30)
				i30 = i30 + 1;
			else if (pl[i].getPlayerSkill() <= 35)
				i35 = i35 + 1;
			else if (pl[i].getPlayerSkill() <= 40)
				i40 = i40 + 1;
			else if (pl[i].getPlayerSkill() <= 45)
				i45 = i45 + 1;
			else if (pl[i].getPlayerSkill() <= 50)
				i50 = i50 + 1;
			else if (pl[i].getPlayerSkill() <= 55)
				i55++;
			else if (pl[i].getPlayerSkill() <= 60)
				i60++;
			else if (pl[i].getPlayerSkill() <= 65)
				i65++;
			else if (pl[i].getPlayerSkill() <= 70)
				i70++;
			else if (pl[i].getPlayerSkill() <= 75)
				i75++;
			else if (pl[i].getPlayerSkill() <= 80)
				i80++;
			else if (pl[i].getPlayerSkill() <= 85)
				i85++;
			else if (pl[i].getPlayerSkill() <= 90)
				i90++;
			else if (pl[i].getPlayerSkill() <= 95)
				i95++;
			else if (pl[i].getPlayerSkill() <= 100)
				i100++;
			if (pl[i].getRank().getEloNumber() <= 800)
				i800++;
			else if (pl[i].getRank().getEloNumber() <= 860)
				i860++;
			else if (pl[i].getRank().getEloNumber() <= 920)
				i920++;
			else if (pl[i].getRank().getEloNumber() <= 980)
				i980++;
			else if (pl[i].getRank().getEloNumber() <= 1040)
				i1040++;
			else if (pl[i].getRank().getEloNumber() <= 1100)
				i1100++;
			else if (pl[i].getRank().getEloNumber() <= 1160)
				i1160++;
			else if (pl[i].getRank().getEloNumber() <= 1220)
				i1220++;
			else if (pl[i].getRank().getEloNumber() <= 1280)
				i1280++;
			else if (pl[i].getRank().getEloNumber() <= 1340)
				i1340++;
			else if (pl[i].getRank().getEloNumber() <= 1400)
				i1400++;
			else if (pl[i].getRank().getEloNumber() <= 1460)
				i1460++;
			else if (pl[i].getRank().getEloNumber() <= 1520)
				i1520++;
			else if (pl[i].getRank().getEloNumber() <= 1580)
				i1580++;
			else if (pl[i].getRank().getEloNumber() <= 1640)
				i1640++;
			else if (pl[i].getRank().getEloNumber() <= 1700)
				i1700++;
			else if (pl[i].getRank().getEloNumber() <= 1760)
				i1760++;
			else if (pl[i].getRank().getEloNumber() <= 1820)
				i1820++;
			else if (pl[i].getRank().getEloNumber() <= 1880)
				i1880++;
			else if (pl[i].getRank().getEloNumber() <= 1940)
				i1940++;

		}
		standartabweichung = (standartabweichung + 0.0) / (pl.length + 0.0);
		double sumRangpunkte = 0.0;
		for (int i = 0; i < pl.length; i++) {
			sumRangpunkte = sumRangpunkte + pl[i].getRank().getEloNumber();
		}
		double dRangpunkte = sumRangpunkte / (pl.length + 0.0);
		mywriter.println("Gesamte Rangpunkte " + sumRangpunkte + " geteilt durch die Anzahl der Spieler, " + pl.length
				+ " ist gleich: " + dRangpunkte);
		mywriter.println("Die durchschnittliche Abweichung zwischen der Platzierung nach Skill und der nach Rang ist: "
				+ standartabweichung);
		mywriter.println("<=5: " + i5 + ", <=10: " + i10 + ", <=15: " + i15 + ", <=20: " + i20 + ", <=25: " + i25
				+ ", <= 30: " + i30 + ", <=35: " + i35 + ", <=40: " + i40 + ", <=45: " + i45 + ", <=50: " + i50
				+ ", <=55: " + i55 + ", <=60: " + i60 + ",<=65: " + i65 + ",<=70: " + i70 + ",<=75: " + i75 + ",<=80: "
				+ i80 + ",<=85: " + i85 + ",<=90: " + i90 + ",<=95: " + i95 + ",<=100: " + i100);
		mywriter.println("<=800: " + i800 + ", <860: " + i860 + ",<=920: " + i920 + ",<=980: " + i980 + ",<=1040: "
				+ i1040 + ",<=1100: " + i1100 + ",<=1160: " + i1160 + ",<=1220: " + i1220 + ",<=1280: " + i1280
				+ ",<=1340: " + i1340 + ",<=1400: " + i1400 + ",<=1460: " + i1460 + ",<=1520: " + i1520 + ",<=1580: "
				+ i1580 + ",<=1640: " + i1640 + ",<=1700: " + i1700 + ",<=1760: " + i1760 + ",<=1820: " + i1820
				+ ",<=1880: " + i1880 + ",<=1940: " + i1940);
		/*
		 * Hier können weitere Kriterien, nach denen Matchmaking-Systeme beurteilt
		 * werden können ausgedruckt werden. mywriter.println();
		 */
		mywriter.close();

	}

	/**
	 * Lässt den Matchmaker bis zu den gewollt gespielten Matches laufen.
	 * 
	 * @param iG
	 *            die Anzahl der zu spielenden Spiele
	 * @param mmSystem
	 *            der Matchmaker
	 * @param pl
	 *            die Spieler, welche Spielen sollen
	 */
	private static void spieleSpieleBis(int iG, Matchmaker mmSystem, Player[] pl, File dir)
			throws FileNotFoundException, UnsupportedEncodingException {
		mmSystem.clearPlayers();
		for (int i = 0; i < pl.length; i++) {
			Matchmaker.enterQueue(pl[i]);
		}
		mmSystem.run();
		printRaenge(iG, pl, dir);
	}

}