package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String, SoccerPlayer> database = new Hashtable<>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        if(database.containsKey(firstName+lastName)) {
            return false;
        }
        database.put(firstName+lastName, new SoccerPlayer(firstName, lastName, uniformNumber, teamName));
        return true;
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        if(database.containsKey(firstName+lastName)) {
            database.remove(firstName+lastName);
            return true;
        }
        return false;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        return database.get(firstName+lastName);
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        if(!database.containsKey(firstName+lastName)) {
            return false;
        }
        database.get(firstName+lastName).bumpGoals();
        return true;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        if(!database.containsKey(firstName+lastName)) {
            return false;
        }
        database.get(firstName+lastName).bumpFouls();
        return true;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        if(!database.containsKey(firstName+lastName)) {
            return false;
        }
        database.get(firstName+lastName).bumpYellowCards();
        return true;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        if(!database.containsKey(firstName+lastName)) {
            return false;
        }
        database.get(firstName+lastName).bumpRedCards();
        return true;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if(teamName == null) {
            return database.size();
        }
        int count = 0;
        for(SoccerPlayer player : database.values()) {
            if(player.getTeamName().equals(teamName)) {
                count++;
            }
        }
        return count;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        ArrayList<SoccerPlayer> players = new ArrayList<>();
        ArrayList<SoccerPlayer> teamPlayers = new ArrayList<>();
        for(SoccerPlayer player : database.values()) {
            players.add(player);
            if(player.getTeamName().equals(teamName)) {
                teamPlayers.add(player);
            }
        }
        if(idx >= players.size()) {
            return null;
        }
        if(teamName != null) {
            if (idx < teamPlayers.size()) {
                return teamPlayers.get(idx);
            } else {
                return null;
            }
        }
        return players.get(idx);
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner inFile = new Scanner(fr);

        String firstName = inFile.nextLine();
        String lastName = inFile.nextLine();
        String teamName = inFile.nextLine();
        int uniform = Integer.parseInt(inFile.nextLine());
        int goals = Integer.parseInt(inFile.nextLine());
        int fouls = Integer.parseInt(inFile.nextLine());
        int yellowCards = Integer.parseInt(inFile.nextLine());
        int redCards = Integer.parseInt(inFile.nextLine());
        SoccerPlayer soccerPlayer = new SoccerPlayer(firstName,lastName,uniform,teamName);
        for(int i = 0; i < goals; i++) {
            soccerPlayer.bumpGoals();
        }
        for(int i = 0; i < fouls; i++) {
            soccerPlayer.bumpFouls();
        }
        for(int i = 0; i < yellowCards; i++) {
            soccerPlayer.bumpYellowCards();
        }
        for(int i = 0; i < redCards; i++) {
            soccerPlayer.bumpRedCards();
        }
        database.put(firstName+lastName, soccerPlayer);
        inFile.close();
        return file.exists();
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            for (SoccerPlayer s : database.values()) {
                pw.println(s.getFirstName());
                pw.println(s.getLastName());
                pw.println(s.getTeamName());
                pw.println(s.getUniform());
                pw.println(s.getGoals());
                pw.println(s.getFouls());
                pw.println(s.getRedCards());
                pw.println(s.getYellowCards());

//                pw.println(logString(s.getFirstName()));
//                pw.println(logString(s.getLastName()));
//                pw.println(logString(s.getTeamName()));
//                pw.println(logString(String.valueOf(s.getUniform())));
//                pw.println(logString(String.valueOf(s.getGoals())));
//                pw.println(logString(String.valueOf(s.getRedCards())));
//                pw.println(logString(String.valueOf(s.getYellowCards())));
            }
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        return new HashSet<String>();
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
