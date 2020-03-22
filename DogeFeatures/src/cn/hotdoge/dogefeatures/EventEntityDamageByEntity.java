package cn.hotdoge.dogefeatures;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventEntityDamageByEntity implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntityType().equals(EntityType.BAT) && e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 0));
			DogeFeatures.ncovLocationsInfoMap.put(p.getUniqueId(), p.getLocation());
		}
	}
}
