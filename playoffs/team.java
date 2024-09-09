/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playoffs;

/**
 *
 * @author shaamir
 */
public class team {
    private String name;
    private int played;
    private int won;
    private int noResult;
    private double nrr;
    private int lost;
    private int points;
    private int position;

    public team(String n, int p, int w, int noR, double net)
    {
        name = n;
        played = p;
        won = w;
        noResult = noR;
        nrr = net;
        lost = p-w;
        points = w*2 + noR;
        position = -1;
    }

    public String getName(){
        return name;
    }
    public int getPlayed(){
        return played;
    }
    public int getWon(){
        return won;
    }
    public int getNoR(){
        return noResult;
    }
    public double getNrr(){
        return nrr;
    }
    public int getLost(){
        return lost;
    }
    public int getPoints(){
        return points;
    }
    public int getPos(){
        return position;
    }


    public void setPlayed(int p){
        played = p;
    }
    public void updatePlayed(int p){
        played += p;
    }


    public void setWon(int w){
        won = w;
    }
    public void updateWon(int w){
        won += w;
    }

    public void setNor(int n){
        noResult = n;
    }
    public void updateNor(int n){
        noResult += n;
    }


    public void setNrr(int nr){
        nrr = nr;
    }


    public void setLost(int l){
        lost = l;
    }
    public void updateLost(int l){
        lost += l;
    }

    public void setPoints(int p){
        points = p;
    }
    public void updatePoints(int p){
        points += p;
    }


    public void setPos(int p){
        position = p;
    }
    public void updatePos(int p){
        position += p;
    }


    public team teamCopy(){
        team t;
        String n = String.valueOf(name);
        t = new team(n,played,won,noResult,nrr);
        t.position = position;
        return t;
    }
    
    public String toString(){
        return "Team: "+ name + ", points: " + points;
    }
}
