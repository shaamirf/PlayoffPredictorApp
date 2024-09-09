/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playoffs;

/**
 *
 * @author shaamir
 */
public class table {
    private team[]standings;
    private int count;
    private int playoffThreshold;
    private int size;
    private int gamesPerTeam;

    public table(int teams, int poth, int gpt){
        standings = new team[teams];
        count = 0;
        playoffThreshold = poth;
        size = teams;
        gamesPerTeam = gpt;
    }

    public void insertTeam(team t){
        if(count>=size) return;
        t.setPos(count+1);
        standings[count] = t;
        sortTable(count);
        count++;
    }

    public team getTeamByName(String n){
        for(int i = 0 ; i<count ;i++){
            if(standings[i].getName().equals(n)) return standings[i];
        }
        return null;
    }

    public team getTeamByPosition(int n){
        if(n<1 || n>count) return null;
        return standings[n-1];
    }

    public int getCount(){
        return count;
    }

    public int getPlayOffThreshold(){
        return playoffThreshold;
    }

    public int getSize(){
        return size;
    }

    public int gamesPerTeam(){
        return gamesPerTeam;
    }

    public team[] getTable(){
       return standings;
    }

    public table tableCopy(){
        table t = new table(size,playoffThreshold,gamesPerTeam);
        for(int i = 0 ; i<count ;i++){
            t.insertTeam(standings[i].teamCopy());
        }
        return t;
     }

    public void sortTable(int pos)
    {
        int newPos = pos;

        while(newPos>0 && standings[newPos].getPoints() > standings[newPos-1].getPoints()){
            team temp = standings[newPos];

            standings[newPos] = standings[newPos - 1];
            standings[newPos].updatePos(1);

            standings[newPos-1] = temp;
            standings[newPos-1].updatePos(-1);

            newPos--;
        }

        while(newPos>0 && standings[newPos].getPoints() == standings[newPos-1].getPoints() && standings[newPos].getNrr() > standings[newPos-1].getNrr()){
            team temp = standings[newPos];

            standings[newPos] = standings[newPos - 1];
            standings[newPos].updatePos(1);

            standings[newPos-1] = temp;
            standings[newPos-1].updatePos(-1);

            newPos--;
        }
    }

    public void printTable()
    {
        System.out.println("Points Table:");
        int i;
        for(i = 0; i < count; i++){
            System.out.print( standings[i].getName() + ": " + standings[i].getPoints() + " points, " + standings[i].getPos());
            if(i%10==0) System.out.println("st place"); 
            else if(i%10==1) System.out.println("nd place");
            else if(i%10==2) System.out.println("rd place");
            else System.out.println("th place");
        }
    }
}




