package cn.hotdoge.dogefeatures.events;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.vars.*;
import net.minecraft.server.v1_15_R1.EntityFox.o;


public class EventPlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(VarWelcomeMessage.playersWhoCannotMove.contains(p.getUniqueId())) {
			e.setCancelled(true);
			p.sendTitle(VarWelcomeMessage.welcomeMessageTitleString, VarWelcomeMessage.welcomeMessageSubTitleString, 5, 40, 5);
			return;
		}
		
		
		if(VarNcov.ncovIsEnabled) {
			
			
			try {
				if(!VarNcov.ncovWhoHasArrayList.contains(p.getUniqueId())) return;
			} catch (Exception err) {
				return;
			}
			
			for(PotionEffect effect:p.getActivePotionEffects()) {
				if(effect.getType().equals(PotionEffectType.POISON)) {
					for(Player pInList:Bukkit.getServer().getOnlinePlayers()) {
						if(!p.getUniqueId().equals(pInList.getUniqueId())) {
							if(p.getWorld().equals(pInList.getWorld())) {
								if(p.getLocation().distanceSquared(pInList.getLocation()) < 40) {
									if(!VarNcov.ncovWhoHasArrayList.contains(pInList.getUniqueId())) VarNcov.ncovWhoHasArrayList.add(pInList.getUniqueId());
									pInList.removePotionEffect(PotionEffectType.POISON);
									pInList.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2000, 0));
									VarNcov.ncovBossBar.addPlayer(pInList);
								}
							}
						}
					}
					return;
				}
			}
			VarNcov.ncovWhoHasArrayList.remove(p.getUniqueId());
			VarNcov.ncovBossBar.removePlayer(p);
			
		}
		
	}
}
