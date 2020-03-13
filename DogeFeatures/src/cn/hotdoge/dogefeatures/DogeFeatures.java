package cn.hotdoge.dogefeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class DogeFeatures extends JavaPlugin {

	static Plugin plugin;
	public static List<UUID> votedPlayersRestart;
	public static ArrayList<BanWantedPlayersObj> banWantedPlayers;
	
	static Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		System.out.println("Visit DogeFeatures' source code on https://github.com/naiveDoge/DogeFeatures !");
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
		
		if(this.getConfig().getBoolean("featureSettings.vote.restart")) Bukkit.getPluginCommand("restartvote").setExecutor(new EventRestartVoteCommand());
		if(this.getConfig().getBoolean("featureSettings.vote.ban")) {
			Bukkit.getPluginCommand("banvotecreate").setExecutor(new EventCreateBanVoteCommand());
			Bukkit.getPluginCommand("banvote").setExecutor(new EventBanVoteCommand());
		}
	}
}
