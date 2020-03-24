package cn.hotdoge.dogefeatures.events;

import java.util.Arrays;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import cn.hotdoge.dogefeatures.DogeFeatures;

public class EventPlayerGamemodeChange implements Listener {

	@EventHandler
	public void onPlayerGamemodeChange(PlayerGameModeChangeEvent e) {
		if(e.getPlayer().isOp()) {
			if(Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(e.getNewGameMode())) {
				for(Player pInList:DogeFeatures.getPlugin().getServer().getOnlinePlayers()) {
					if(!pInList.isOp()) {
						pInList.hidePlayer(DogeFeatures.getPlugin(), e.getPlayer());
					}
				}
			}else {
				for(Player pInList:DogeFeatures.getPlugin().getServer().getOnlinePlayers()) {
					pInList.showPlayer(DogeFeatures.getPlugin(), e.getPlayer());
				}
			}
		}
	}
}
