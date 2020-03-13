package cn.hotdoge.dogefeatures;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class EventEatFood extends JavaPlugin implements Listener {
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if(new ItemStack(Material.PUFFERFISH).isSimilar(e.getItem()) && this.getConfig().getBoolean("featureSettings.pufferfish")) {
			e.setCancelled(true);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.5F, 1F);
			p.sendTitle("干了 奥里给!", "干就完事了", 10, 70, 20);
			
			p.getInventory().removeItem(new ItemStack(Material.PUFFERFISH, 1));
			p.updateInventory();
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 140, 0));
			
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				
				@Override
				public void run() {
					//p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 500, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 2));
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 1200, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 800, 0));
				}
			}, 180);
			
		}
	}
}
