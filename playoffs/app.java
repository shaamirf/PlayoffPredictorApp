/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playoffs;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author shaamir
 */
public class app {
    public static void main(String[] args) throws IOException{
        // Fetch the HTML content from the URL
        Document doc = Jsoup.connect("https://www.espncricinfo.com/series/indian-premier-league-2024-1410320/points-table-standings").get();
        Elements body = doc.select("tbody.ds-text-center");
        int teams = 0;
        for(Element e : body.select("tr"))
        {
            String s = e.select("span.ds-text-tight-s.ds-font-bold.ds-uppercase.ds-text-left.ds-text-typo").text();
            if(s.equals("")) continue;
            teams++;
        }
       
        
        int[] scenarios = {0,0,0,0,0};
        /*
        scenarios[0] = guaranteed total
        scenarios[1] = nnr based total
        scenarios[2] = total
        scenarios[3] = number of guaranteed to display
        scenarios[4] = number of nrr based to display
        */
        table t = new table(teams,4,14);
        game[]games = new game[6];
        game[][]guaranteedOutcomes = new game[2][games.length];
        game[][]nrrOutcomes = new game[2][games.length];
        
        for(Element e : body.select("tr"))
        {
            String s = e.select("span.ds-text-tight-s.ds-font-bold.ds-uppercase.ds-text-left.ds-text-typo").text();
            if(s.equals("")) continue;
            
            String s2 = e.select("td.ds-w-0.ds-whitespace-nowrap.ds-min-w-max").text();
            
            ArrayList<String> arr = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(s2," ");
            while (tokenizer.hasMoreElements()) {
                arr.add(tokenizer.nextToken());
            }
            
            
            int m = Integer.parseInt(arr.get(0));
            int w = Integer.parseInt(arr.get(1));
            int noRes = Integer.parseInt(arr.get(4));
            double nrr = Double.parseDouble(arr.get(6));
            
            t.insertTeam(new team(String.valueOf(s),m,w,noRes,nrr));
        }
        
        
        
        t.printTable();
       

        games[0] = new game("Rajasthan Royals","Punjab Kings");
        games[1] = new game("Sunrisers Hyderabad","Gujarat Titans");
        games[2] = new game("Mumbai Indians","Lucknow Super Giants");
        games[3] = new game("Royal Challengers Bengaluru","Chennai Super Kings");
        games[4] = new game("Sunrisers Hyderabad","Punjab Kings");
        games[5] = new game("Rajasthan Royals","Kolkata Knight Riders");

        Scanner in = new Scanner(System.in);

        //t = event("Royal Challengers Bengaluru","Chennai Super Kings",1,t);
        
        t.printTable();
        
        System.out.print("\nEnter Team: ");
        String n = in.nextLine();
        System.out.println();

        boolean run = true;
        int start = 0;

        while(run){
            scenarios[0] = 0; scenarios[1] = 0; scenarios[2] = 0; scenarios[3] = 0; scenarios[4] = 0;
            scenario(n,scenarios,t,games,1,guaranteedOutcomes,nrrOutcomes,start);
            System.out.println();
            System.out.println(scenarios[0] + " guaranteed scenarios, " + scenarios[0] / Math.pow(2,games.length) * 100 + "% chance");
            System.out.println(scenarios[1] + " possible scenarios based on nrr, " + scenarios[1] / Math.pow(2,games.length) * 100 + "% chance");
            System.out.println((scenarios[0]+scenarios[1]) + " total possible scenarios, " + (scenarios[0]+scenarios[1]) / Math.pow(2,games.length) * 100 + "% chance");
            
            System.out.println("\nWhat would you like to do? ");
            System.out.println("1. Show more scenarios\n2. Return");
            System.out.print("Enter Option: ");
            int opt = in.nextInt();
            in.nextLine();

            if(opt==2) run = false;
            else if(opt == 1){
                start = (int)(Math.random() * scenarios[2]);
            }
            else{
                System.out.println("Error");
                run = false;
            }
        }
        
        in.close();
       
    }
    public static void scenario(String name,int[]s,table tab,game[]games,int game,game[][]o,game[][]o2, int start){
        team t = tab.getTeamByName(name);
        if (t == null) {
            System.out.println("Team with name '" + name + "' not found in the table.");
            return;
        }

        if(game>games.length)
        {
            if(t.getPos() <= tab.getPlayOffThreshold())
            {
                s[0]++;
                s[2]++;
                if(s[2]>start && s[3]<o.length){
                    System.out.println();
                    insertOutcome(games,o,s[3]);
                    System.out.println("----GUARANTEED----: Scenario " + (s[2]) + ":");
                    for(int i = 0;i<games.length;i++){
                        System.out.println(o[s[3]][i]);
                    }
                    System.out.println();
                    s[3]++;
                    
                    tab.printTable();
                }
            }
            else if(t.getPoints() == tab.getTable()[tab.getPlayOffThreshold()-1].getPoints())
            {
                s[1]++;
                s[2]++;
                if(s[2]>start && s[4]<o2.length){
                    System.out.println();
                    insertOutcome(games,o2,s[4]);
                    System.out.println("----NRR BASED----: Scenario " + (s[2]) + ":");
                    for(int i = 0;i<games.length;i++){
                        System.out.println(o2[s[4]][i]);
                    }
                    System.out.println();
                    s[4]++;

                    tab.printTable();
                }
            }
            return;
        }

        if((tab.gamesPerTeam() - t.getPlayed()) * 2 + t.getPoints() < tab.getTable()[tab.getPlayOffThreshold()-1].getPoints()) return;

        table one = event(games[game-1].getTeam1(),games[game-1].getTeam2(),1,tab);
        games[game-1].setWinner(1);
        scenario(name,s,one,games,game+1,o,o2,start);

        table two = event(games[game-1].getTeam1(),games[game-1].getTeam2(),2,tab);
        games[game-1].setWinner(2);
        scenario(name,s,two,games,game+1,o,o2,start);
    };

    public static table event(String one, String two, int winner, table t){
        table newT = t.tableCopy();
        team team1 = newT.getTeamByName(one);
        team team2 = newT.getTeamByName(two);

        team1.updatePlayed(1);
        team2.updatePlayed(1);

        if(winner==0)
        {
            team1.updateNor(1);
            team1.updatePoints(1);
            team2.updateNor(1);
            team2.updatePoints(1);
        }
        if(winner==1)
        {
            team1.updateWon(1);
            team1.updatePoints(2);
        }
        if(winner==2)
        {
            team2.updateWon(1);
            team2.updatePoints(2);
        }
        newT.sortTable(team1.getPos()-1);
        newT.sortTable(team2.getPos()-1);

        return newT;
    }

    public static void insertOutcome(game[]g,game[][]o,int i)
    {
        for(int j = 0;j<g.length;j++){
            o[i][j] = g[j].gameCopy();
        }
    }


}

