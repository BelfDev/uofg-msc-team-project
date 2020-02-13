package com.toptrumps.db;
import java.util.*;
import com.toptrumps.core.player.Player;
public class PlayersList{

    public PlayersList(){
        players = new ArrayList<Player>();
        fillPlayerList();
        
    }

    protected List<Player> players;

    public void fillPlayerList(){
        
        this.players.add(new Player(0,"Human"));
        this.players.add(new Player(1,"AiOne"));
        this.players.add(new Player(2,"AiTwo"));
        this.players.add(new Player(3,"AiThree"));
        this.players.add(new Player(4,"AiFour"));
    }

    public Player getPlayer(int id){
        return players.get(id);
    }
}