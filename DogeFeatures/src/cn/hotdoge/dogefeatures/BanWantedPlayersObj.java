package cn.hotdoge.dogefeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class BanWantedPlayersObj {
	
	public UUID Player;
	public List<UUID> Voted;
	public Player Creater;
	
	public BanWantedPlayersObj(UUID k, ArrayList<UUID> v, Player c) {
		Player = k;
		Voted = v;
		Creater = c;
	}
}
