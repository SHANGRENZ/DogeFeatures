package cn.hotdoge.dogefeatures.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.methods.CommonMethods;
import cn.hotdoge.dogefeatures.vars.VarWelcomeMessage;

public class EventPlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		//welcome msg
		if(DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.welcomeMessage")) {
			if(!e.getPlayer().hasPlayedBefore()) {
				for(String line:DogeFeatures.getPlugin().getConfig().getStringList("welcomeMessage.content")) {
			        e.getPlayer().sendMessage(CommonMethods.getColorfulText(line));
				}
		        
				VarWelcomeMessage.playersWhoCannotMove.add(e.getPlayer().getUniqueId());
			}
		}
		
		//hide op
		if(DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.hideOp")) {
			for(Player pInList:DogeFeatures.getPlugin().getServer().getOnlinePlayers()) {
				if(pInList.isOp()) {
					for(Player pInList1:DogeFeatures.getPlugin().getServer().getOnlinePlayers()) {
						if(!pInList1.isOp()) {
							pInList1.hidePlayer(DogeFeatures.getPlugin(), pInList);
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
