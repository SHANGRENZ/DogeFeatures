package cn.hotdoge.dogefeatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import cn.hotdoge.dogefeatures.events.EventBanVoteCommand;
import cn.hotdoge.dogefeatures.events.EventCreateBanVoteCommand;
import cn.hotdoge.dogefeatures.events.EventEatFood;
import cn.hotdoge.dogefeatures.events.EventEntityDamageByEntity;
import cn.hotdoge.dogefeatures.events.EventPlayerChat;
import cn.hotdoge.dogefeatures.events.EventPlayerDie;
import cn.hotdoge.dogefeatures.events.EventPlayerGamemodeChange;
import cn.hotdoge.dogefeatures.events.EventPlayerJoin;
import cn.hotdoge.dogefeatures.events.EventPlayerLeave;
import cn.hotdoge.dogefeatures.events.EventPlayerMove;
import cn.hotdoge.dogefeatures.events.EventRestartVoteCommand;
import cn.hotdoge.dogefeatures.init.InitNcov;
import cn.hotdoge.dogefeatures.init.InitWelcomeMessage;
import cn.hotdoge.dogefeatures.vars.VarNcov;


public class DogeFeatures extends JavaPlugin {

	static Plugin plugin;
	public static List<UUID> votedPlayersRestart;
	public static ArrayList<BanWantedPlayersObj> banWantedPlayers;
	
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		System.out.println("Visit DogeFeatures' source code on https://github.com/naiveDoge/DogeFeatures !");
		System.setProperty("file.encoding", "UTF-8");
		this.saveDefaultConfig();
		
		//auto fill config.yml
		for(String key:this.getConfig().getDefaults().getKeys(true)) {
			if(this.getConfig().get(key).equals(this.getConfig().getDefaults().get(key))) {
				this.getConfig().set(key, this.getConfig().getDefaults().get(key));
			}
		}
		this.saveConfig();
		
		
		votedPlayersRestart = new ArrayList<UUID>();
		banWantedPlayers = new ArrayList<BanWantedPlayersObj>();
		
		Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerLeave(), this);
		Bukkit.getPluginManager().registerEvents(new EventEatFood(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerGamemodeChange(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerDie(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityDamageByEntity(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerMove(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerChat(), this);
		
		if(this.getConfig().getBoolean("featureSettings.vote.restart")) Bukkit.getPluginCommand("restartvote").setExecutor(new EventRestartVoteCommand());
		if(this.getConfig().getBoolean("featureSettings.vote.ban")) {
			Bukkit.getPluginCommand("banvotecreate").setExecutor(new EventCreateBanVoteCommand());
			Bukkit.getPluginCommand("banvote").setExecutor(new EventBanVoteCommand());
		}
		
		new InitNcov();
		new InitWelcomeMessage();
	}
}
