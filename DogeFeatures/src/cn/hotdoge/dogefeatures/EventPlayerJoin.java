package cn.hotdoge.dogefeatures;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventPlayerJoin extends JavaPlugin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		//welcome msg
		if(this.getConfig().getBoolean("featureSettings.welcomeMessage")) {
			if(!e.getPlayer().hasPlayedBefore()) {
				e.getPlayer().sendMessage(this.getConfig().getString("welcomeMessage"));
			}
		}
		
		//hide op
		if(this.getConfig().getBoolean("featureSettings.hideOp")) {
			for(Player pInList:this.getServer().getOnlinePlayers()) {
				if(pInList.isOp()) {
					for(Player pInList1:this.getServer().getOnlinePlayers()) {
						if(!pInList1.isOp()) {
							pInList1.hidePlayer(this, pInList);
						}
					}
				}
			}
			if(e.getPlayer().isOp()) {
				e.setJoinMessage(null);
			}
		}
		
	}
}
