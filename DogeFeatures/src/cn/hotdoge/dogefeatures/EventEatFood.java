package cn.hotdoge.dogefeatures;

import java.util.Collection;
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
	
	Collection<PotionEffect> activeEffects;
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if(new ItemStack(Material.PUFFERFISH).isSimilar(e.getItem()) && DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.pufferfish")) {
			activeEffects = p.getActivePotionEffects();
			
			p.sendTitle(ChatColor.YELLOW + "干了 奥里给!", "干就完事了", 10, 70, 20);
			
			Bukkit.getScheduler().runTaskLater(DogeFeatures.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					activeEffects = p.getActivePotionEffects();
					
					for(PotionEffect effect:activeEffects) {
						p.removePotionEffect(effect.getType());
					}
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 140, 0));
				}
			}, 5);
			Bukkit.getScheduler().runTaskLater(DogeFeatures.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					for(PotionEffect effect:activeEffects) {
						p.addPotionEffect(effect);
					}
					
					//p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 500, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 3200, 3));
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 2500, 2));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1400, 1));
				}
			}, 180);
			
			p.setMetadata("timePufferfishLastEat", new FixedMetadataValue(DogeFeatures.getPlugin(), new Date().getTime()));
			
		}
	}
}
