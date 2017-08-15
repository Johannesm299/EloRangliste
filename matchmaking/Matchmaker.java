package matchmaking;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.io.File;

import player.Player;

/**
 * Matchmaking ist die Klasse in der alle Spieler gespeichert sind, die nach Spielen suchen, sowie alle Spiele, die derzeit noch nicht voll sind und noch Spieler benötigen.
 * 
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */

public class Matchmaker implements Runnable {
	private static int matchesPlayed = 0;
	private static Matchmaker instance;
	private static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private static int lowerp = 0;
	private static int upperp = 0;
	private static HashMap<Integer, Match> matches = new HashMap<Integer, Match>();
	private static int lowerm = 0;
	private static int upperm = 0;
	private static int intervallOG;
	private static File directory = new File("Matches");
	
	/**
	 * Konstruktor fuer die Klasse Matchmaker
	 */
	private Matchmaker(){
		
	}
	/**
	 * Sicherstellung, dass nur ein Objekt dieser Klasse existiert.
	 * @return Matchmaker-Instanz
	 */
	public static synchronized Matchmaker getInstance(){
		if(Matchmaker.instance==null){
			Matchmaker.instance = new Matchmaker();
			directory.mkdir();
		}
		return Matchmaker.instance;
	}
	/**
	 * Ordnet einen Spieler den Spielsuchenden Spielern bei.
	 * @param p Spieler der die Queue/das Matchmaking betritt
	 */
	public static synchronized void enterQueue(Player p){
		System.out.println(p.getName() + " betritt die q");
		p.startSearching();
		players.put(upperp,p);
		upperp++;
	}
	/**
	 * Ordnet einen Spieler einem Match zu und entfernt ihn von den Spielsuchenden Spielern.
	 * @param p Spieler der zugeordnet wird
	 * @param m Match dem der Spieler zugeteilt wird
	 * @param k Index des Spielers, der einem Spiel zugeteilt wird
	 * @param j Index des Spiels dem der Spieler zugeteilt wird
	 */
	private void playerPlaysMatch(Player p, Match m, int k, int j){
		System.out.println(p.getName() + " tritt Match " + m.getId() + " bei");
		m.addPlayer(p);
		for(int i=k; i>lowerp; i--){
			players.put(i, players.get(i-1));
		}
		players.remove(lowerp);
		lowerp++;
		if(m.matchIsFull()){
			System.out.println("Match " + m.getId() + " ist voll, es startet.");
			for(int i=j; i>lowerm; i--){
				System.out.println("Match an " + i + " ist Match: " + matches.get(i));
				matches.put(i, matches.get(i-1));
				System.out.println("Match nach Verschiebung an " + i + " ist Match: " + matches.get(i));
			}
			matches.remove(lowerm);
			lowerm++;
			m.run();
			Matchmaker.increaseMPlayed();
			System.out.println("Match " + m.getId() + " ist fertig");
		}
	}
	/**
	 * Erstellt ein neues Match, nachdem kein geeignetes für einen Spieler gefunden wurde.
	 * @param j der Spieler fuer den ein neues Match erstellt wird
	 */
	private synchronized void createNewMatch(int j) throws FileNotFoundException, UnsupportedEncodingException{
		matches.put(upperm,new Match(players.get(j), directory));
		System.out.println("neues Match " + matches.get(upperm).getId() + "-----------------------------------------");
		System.out.println("mit " + players.get(j).getName());
		upperm++;
		for(int k=j;k>lowerp;k--){
			players.put(j, players.get(j-1));
		}
		players.remove(lowerp);
		lowerp++;
	}
	/**
	 * Erhoeht die Anzahl der bereits gespielten Spiele
	 */
	private synchronized static void increaseMPlayed(){
		matchesPlayed++;
	}
	/**
	 * Löscht alle Spielsuchenden Spieler.
	 */
	public void clearPlayers(){
		players.clear();
		lowerp=0;
		upperp=0;
	}
	/**
	 * Setzt eine Obergrenze an zu spielenden Matches
	 * @param i die Obergrenze an zu spielenden Matches
	 */
	public void setIntervallOG(int i){
		intervallOG=i;
	}
	/**
	 * Durchsucht die Spieler und Matches nach passenden Spielern fuer ein Match, anhand des gameRanks der Matches und des Rangs des Spielers.
	 * Lässt ein Match starten, sobald es voll ist.
	 * Dokumentiert alle gespielten Spiele im Workplace.
	 * Stoppt, wenn die Obergrenze erreicht ist.
	 */
	public void run() {
		for(int i=lowerp;i<upperp;i++){
			System.out.println(players.get(i).getName() + " " + players.get(i).getRank().getEloNumber());
		}
			while(lowerp<upperp&&matchesPlayed<=intervallOG){
				for(int j = lowerp; j<upperp; j++){
					boolean foundGame=false;
					if(matchesPlayed<=intervallOG){
						for(int i = lowerm; i<upperm; i++){
							if(players.get(j).getRank().getEloNumber()>=1500.0){
								if(players.get(j).getRank().getEloNumber()
								>=0.9*matches.get(i).getGameRank()
								&& players.get(j).getRank().getEloNumber()
								<=1.1*matches.get(i).getGameRank()){
										
										playerPlaysMatch(players.get(j), matches.get(i), j, i);
										foundGame=true;
										break;
								}
							}
							if(players.get(j).getRank().getEloNumber()<1500.0&&players.get(j).getRank().getEloNumber()>=1000.0){
								if(players.get(j).getRank().getEloNumber()
								>=0.8*matches.get(i).getGameRank()
								&&players.get(j).getRank().getEloNumber()
								<=1.2*matches.get(i).getGameRank()){
										playerPlaysMatch(players.get(j), matches.get(i), j, i);
										foundGame=true;
										break;
								}
							}
							if(players.get(j).getRank().getEloNumber()<1000.0){
								if(players.get(j).getRank().getEloNumber()
										>=0.7*matches.get(i).getGameRank()
										&& players.get(j).getRank().getEloNumber()
										<=1.3*matches.get(i).getGameRank()){
												
												playerPlaysMatch(players.get(j), matches.get(i), j, i);
												foundGame=true;
												break;
										}
							}
						}
					}
					else{
						break;
					}
					if(foundGame==false){
						try {
							createNewMatch(j);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
	}

}
