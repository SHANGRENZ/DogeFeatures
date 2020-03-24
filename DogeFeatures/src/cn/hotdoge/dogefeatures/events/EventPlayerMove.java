package cn.hotdoge.dogefeatures;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class EventPlayerMove implements Listener {

	public static boolean ncovIsEnabled;
	public static BossBar ncovTimeLeft;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(ncovIsEnabled) {
			
			Player p = e.getPlayer();
			
			try {
				if(DogeFeatures.ncovLocationsInfoMap.get(p.getUniqueId()).equals(null)) return;
			} catch (Exception err) {
				return;
			}
			
			for(PotionEffect effect:p.getActivePotionEffects()) {
				if(effect.getType().equals(PotionEffectType.POISON)) {
					for(Player pInList:Bukkit.getServer().getOnlinePlayers()) {
						if(p.getWorld().equals(pInList.getWorld())) {
							if(p.getLocation().distanceSquared(pInList.getLocation()) < 40) {
								DogeFeatures.ncovLocationsInfoMap.put(pInList.getUniqueId(), pInList.getLocation());
								pInList.removePotionEffect(PotionEffectType.POISON);
								pInList.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 0));
								ncovTimeLeft.addPlayer(pInList);
							}
						}
					}
					return;
				}
			}
			DogeFeatures.ncovLocationsInfoMap.remove(p.getUniqueId());
			ncovTimeLeft.removePlayer(p);
			
		}
	}
}
