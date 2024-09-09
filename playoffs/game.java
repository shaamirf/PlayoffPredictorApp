/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playoffs;

/**
 *
 * @author shaamir
 */
public class game {
    private String team1;
    private String team2;
    private int winner;

    public game(String one, String two)
    {
        team1 = one;
        team2 = two;
        winner = -1;
    }

    public String getTeam1(){
        return team1;
    }

    public String getTeam2(){
        return team2;
    }

    public int getWinner(){
        return winner;
    }

    public void setTeam2(String t){
        team2 = t;
    }

    public void setTeam1(String t){
        team1 = t;
    }

    public void setWinner(int n){
        winner = n;
    }
   
    public game gameCopy(){
        game newG = new game(String.valueOf(team1),String.valueOf(team2));
        newG.setWinner(winner);
        return newG;
    }

    public String toString(){
        if(winner==0) return "no result: " + team1 + " vs " + team2;
        if(winner==1) return team1 + " beats " + team2;
        if(winner==2) return team2 + " beats " + team1;
        else return team1 + " vs " + team2;
    }
}
