package ranking;

import player.Player;

/**
 * Playerrating ist für die Bewertung der Spieler mit einer Elozahl zuständig,
 * zu Anfang wird der default Wert 1200 gegeben, welcher nach jedem Spiel angepasst wird.
 * 
 * @author Johannes Schürmeyer
 * @version 1.0
 *
 */

public class Playerrating {
	
	private double eloNumber;
	private boolean adjusted = false;
	private final Player p;
	
	/**
	 * default Konstruktor fuer die Klasse Playerrating
	 * @param p der Spieler dem dieses Objekt zugeordnet wird
	 */
	public Playerrating(Player p){
		this.p=p;
		eloNumber = 1200;
	}
	/**
	 * spezieller Konstruktor fuer die Klasse Playerrating
	 * es kann eine beliebige Elozahl gegeben werden
	 * @param n die zugeteilte Elozahl
	 * @param p der Spieler dem dieses Objekt zugeordnet wird
	 */
	public Playerrating(double n, Player p){
		this.p=p;
		eloNumber = n;
	}
	/**
	 * Getter fuer die Elozahl
	 * @return Elozahl des Spielers
	 */
	public double getEloNumber(){
		return eloNumber;
	}
	/**
	 * Passt die Elozahl des Spielers nach dem Spiel an
	 * @param SR1 Summe der Elozahlen von Team 1
	 * @param SR2 Summe der Elozahlen von Team 2
	 * @param team Teamangehörigkeit des Spielers, dessen Rang angepasst wird
	 * @param winner Gewinner des Spiels
	 * @param rT1 gewonnene Runden von Team 1
	 * @param rT2 gewonnene Runden von Team 2
	 * @param punkteS Punktzahl(Score) des Spielers
	 * @param punkteT Punktzahl(Score) des Teams
	 */
	public void adjustRanking(double SR1, double SR2, int team, int winner,int rT1,int rT2, int punkteS, int punkteT){
		double lamda= 0.5;
		double i;
		double d;
		double erwartungswert;
		int k;
		if(p.getGames()<=300){
			k = (40-(p.getGames()/10));
		}
		else
			k=10;
		//nicht unentschieden
		if(winner==1||winner==2){
			//spieler in team1
			if(team==1){
				
				erwartungswert=1/(1+Math.pow(10, (SR2-SR1)/400.0));
				//team1 hat gewonnen
				if(winner==1&&adjusted==false){
					d = (k*(1-erwartungswert));
					d=(d/2)+((rT1-rT2)/10.0)*(d/2);
					//System.out.println(d/2 + " " + ((rT1-rT2)/10.0)*(d/2));
					i = (punkteS+0.0)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
					//System.out.println(eloNumber);
					adjusted=true;
				}
				//team1 hat verloren
				if(winner==2&&adjusted==false){
					d=(k*(0-erwartungswert));
					d=(d/2)+((rT2-rT1)/10.0)*(d/2);
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
					adjusted=true;
				}
			}
			//spieler in team2
			else if(team==2){
				
				erwartungswert=1/(1+Math.pow(10, (SR1-SR2)/400.0));
				//team2 hat gewonnen
				if(winner==2&&adjusted==false){
					d=(k*(1-erwartungswert));
					d=(d/2)+((rT2-rT1)/10.0)*(d/2);
					i = (punkteS+0.0)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
					adjusted=true;
				}
				//team2 hat verloren
				if(winner==1&&adjusted==false){
					d=(k*(0-erwartungswert));
					d=(d/2)+((rT1-rT2)/10.0)*(d/2);
					//System.out.println(d/2 + " " + ((rT1-rT2)/10.0)*(d/2));
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
					adjusted=true;
				}
			}
		}
		//spiel ist unentschieden
		if(winner==3){
			//spieler in team 1
			if(team==1&&adjusted==false){
				erwartungswert=	1/(1+Math.pow(10, (SR2-SR1)/400));
				d=(k*(0.5-erwartungswert));
				//System.out.println("D: " + d);
				if(d>0){
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
				}
				if(d<0){
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
				}
				adjusted=true;
			}
			if(team==2&&adjusted==false){
				erwartungswert=1/(1+Math.pow(10, (SR1-SR2)/400));
				d=(k*(0.5-erwartungswert));
				//System.out.println("D: " + d);
				if(d>0){
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
				}
				if(d<0){
					i= (((2*punkteT)/5.0)-punkteS)/(punkteT+0.0);
					eloNumber=eloNumber+(lamda*d+i*(1-lamda)*d*5);
				}
				adjusted=true;
			}
		}
		adjusted=false;
	}
	

}
