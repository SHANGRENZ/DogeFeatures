package cn.hotdoge.dogefeatures.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cn.hotdoge.dogefeatures.DogeFeatures;



public class EventEatFood implements Listener {
	
	Collection<PotionEffect> activeEffects;
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		if(new ItemStack(Material.PUFFERFISH).isSimilar(e.getItem()) && DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.pufferfish")) {
			try {
				if(p.getMetadata("isPufferfishJustEat").get(0).asBoolean()) {
					e.setCancelled(true);
					p.sendTitle(ChatColor.YELLOW + "口区——", "你短时间不能食用太多的奥利给", 5, 20, 5);
					return;
				}
			} catch (Exception e2) {
				//To do nothing
			}
			
			activeEffects = p.getActivePotionEffects();
			
			p.sendTitle(ChatColor.YELLOW + "干了 奥里给!", "干就完事了", 10, 70, 10);
			
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
					
					p.setMetadata("isPufferfishJustEat", new FixedMetadataValue(DogeFeatures.getPlugin(), true));
					Bukkit.getScheduler().runTaskLater(DogeFeatures.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							for(PotionEffect effect:activeEffects) {
								if(!Arrays.asList(PotionEffectType.POISON, PotionEffectType.HUNGER, PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS).contains(effect.getType())) {
									p.addPotionEffect(effect);
								}
							}
							
							int[] effectLengths = {3200, 2500, 2000, 1400};
							int[] effectLevels = {5, 2, 1, 1};
							int effectIndex = 0;
							for(PotionEffectType effectType:Arrays.asList(PotionEffectType.POISON, PotionEffectType.HUNGER, PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS)) {
								for(PotionEffect effect:activeEffects) {
									if(effectType.equals(effect.getType())) {
										effectLengths[effectIndex] += effect.getDuration();
									}
								}
								p.addPotionEffect(new PotionEffect(effectType, effectLengths[effectIndex], effectLevels[effectIndex]));
								effectIndex++;
							}
							
							p.setMetadata("isPufferfishJustEat", new FixedMetadataValue(DogeFeatures.getPlugin(), false));
							
						}
					}, 180);
				}
			}, 1);
			
			p.setMetadata("timePufferfishLastEat", new FixedMetadataValue(DogeFeatures.getPlugin(), new Date().getTime()));
			
		}
	}
}
