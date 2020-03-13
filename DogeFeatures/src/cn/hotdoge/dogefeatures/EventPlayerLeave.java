package cn.hotdoge.dogefeatures;


import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerLeave implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		UUID uuidFromPlayer = e.getPlayer().getUniqueId();
		
		for(UUID uuidInList:DogeFeatures.votedPlayersRestart) {
			if(uuidFromPlayer.equals(uuidInList)) {
				DogeFeatures.votedPlayersRestart.remove(uuidInList);
			}
		}
	}
}
