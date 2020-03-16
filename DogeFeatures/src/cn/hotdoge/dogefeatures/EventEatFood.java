package cn.hotdoge.dogefeatures;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class EventEatFood implements Listener {
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if(new ItemStack(Material.PUFFERFISH).isSimilar(e.getItem()) && DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.pufferfish")) {
			e.setCancelled(true);
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.5F, 1F);
			p.sendTitle(ChatColor.YELLOW + "干了 奥里给!", "干就完事了", 10, 70, 20);
			p.setFoodLevel(p.getFoodLevel() < 20 ? p.getFoodLevel() + 1 : p.getFoodLevel());
			
			p.getInventory().removeItem(new ItemStack(Material.PUFFERFISH, 1));
			p.updateInventory();
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 140, 0));
			
			Bukkit.getScheduler().runTaskLater(DogeFeatures.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					//p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 500, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 3000, 7));
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 2200, 5));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1400, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2000, 1));
				}
			}, 180);
			
			p.setMetadata("timePufferfishLastEat", new FixedMetadataValue(DogeFeatures.getPlugin(), new Date().getTime()));
			
		}
	}
}
