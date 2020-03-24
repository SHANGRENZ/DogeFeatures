package cn.hotdoge.dogefeatures.events;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.vars.VarNcov;

public class EventPlayerDie implements Listener {
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		if(e.getEntityType().equals(EntityType.PLAYER)) {
			try {
				long lastEat = e.getEntity().getMetadata("timePufferfishLastEat").get(0).asLong();
				if(new Date().getTime() - lastEat < 100000) {
					if(e.getEntity().getKiller() instanceof Player) {
						e.setDeathMessage(e.getEntity().getName() + "食用过多的" + ChatColor.YELLOW + "奥利给" + ChatColor.WHITE + "后被" + e.getEntity().getKiller().getName() + "杀死了");
					}else {
						e.setDeathMessage(e.getEntity().getName() + "因食用过多的" + ChatColor.YELLOW + "奥利给" + ChatColor.WHITE + "而去世了");
					}
					e.getEntity().setMetadata("timePufferfishLastEat", new FixedMetadataValue(DogeFeatures.getPlugin(), 0));
					return;
				}
			} catch (Exception e2) {
				//To do nothing
			}
			
			try {
				if(VarNcov.ncovWhoHasArrayList.contains(e.getEntity().getUniqueId())) {
					VarNcov.ncovWhoHasArrayList.remove(e.getEntity().getUniqueId());
					VarNcov.ncovBossBar.removePlayer(e.getEntity());
					if(e.getEntity().getKiller() instanceof Player) {
						e.setDeathMessage(e.getEntity().getName() + "在感染Coronavirus后被" + e.getEntity().getKiller().getName() + "杀死了");
					}else {
						e.setDeathMessage(e.getEntity().getName() + "因感染Coronavirus而去世了");
					}
				}
			} catch (Exception err) {
				// To do nothing.
			}
		}
	}
}
