package cn.hotdoge.dogefeatures;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EventPlayerDie implements Listener {
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		if(e.getEntityType().equals(EntityType.PLAYER)) {
			try {
				long lastEat = e.getEntity().getMetadata("timePufferfishLastEat").get(0).asLong();
				if(new Date().getTime() - lastEat < 100000) {
					e.setDeathMessage(e.getEntity().getName() + "因食用过多的" + ChatColor.YELLOW + "奥利给" + ChatColor.WHITE + "而去世了");
					e.getEntity().setMetadata("timePufferfishLastEat", new FixedMetadataValue(DogeFeatures.getPlugin(), 0));
					return;
				}
			} catch (Exception e2) {
				//To do nothing
			}
			
			try {
				if(!DogeFeatures.ncovLocationsInfoMap.get(e.getEntity().getUniqueId()).equals(null)) {
					DogeFeatures.ncovLocationsInfoMap.remove(e.getEntity().getUniqueId());
					EventPlayerMove.ncovTimeLeft.removePlayer(e.getEntity());
					e.setDeathMessage(e.getEntity().getName() + "因感染Coronavirus而去世了");
				}
			} catch (Exception err) {
				// To do nothing.
			}
		}
	}
}
