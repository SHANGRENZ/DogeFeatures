package cn.hotdoge.dogefeatures.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.vars.VarNcov;

public class EventEntityDamageByEntity implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntityType().equals(EntityType.BAT) && e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 0));
			try {
				if(VarNcov.ncovWhoHasArrayList.contains(p.getUniqueId())) VarNcov.ncovWhoHasArrayList.remove(p.getUniqueId());
				VarNcov.ncovWhoHasArrayList.add(p.getUniqueId());
			} catch (Exception err) {
				VarNcov.ncovWhoHasArrayList.add(p.getUniqueId());
			}
			VarNcov.ncovBossBar.addPlayer(p);
		}
	}
}
