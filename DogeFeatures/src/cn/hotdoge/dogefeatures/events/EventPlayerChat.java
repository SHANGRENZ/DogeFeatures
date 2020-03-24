package cn.hotdoge.dogefeatures.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.vars.VarWelcomeMessage;

public class EventPlayerChat implements Listener {
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		if(VarWelcomeMessage.playersWhoCannotMove.contains(p.getUniqueId())) {
			
			e.setCancelled(true);
			if(e.getMessage().equals(DogeFeatures.getPlugin().getConfig().getString("welcomeMessage.confirmMsg"))) {
				p.sendMessage(ChatColor.GREEN + "成功!您可以开始游玩了!");
				VarWelcomeMessage.playersWhoCannotMove.remove(p.getUniqueId());
				return;
			}
			
			p.sendMessage(ChatColor.RED + "您现在还不能发送消息");
			
		}
	}

}
