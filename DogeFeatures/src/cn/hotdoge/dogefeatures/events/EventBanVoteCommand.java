package cn.hotdoge.dogefeatures.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cn.hotdoge.dogefeatures.BanWantedPlayersObj;
import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.*;

public class EventBanVoteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		String targetPlayerName;
		try {
			targetPlayerName = args[0];
		} catch (Exception e) {
			p.sendMessage("用法:/banvote player 投票/取消投票封禁某个玩家");
			return true;
		}
		UUID targetPlayerUuid = DogeFeatures.getPlugin().getServer().getOfflinePlayer(targetPlayerName).getUniqueId();
		
		int x = 0;
		for(BanWantedPlayersObj pObj:DogeFeatures.banWantedPlayers) {
			if(pObj.Player.equals(targetPlayerUuid)) {
				//target available
				int y = 0;
				for(UUID uuidInList:pObj.Voted) {
					if(uuidInList.equals(p.getUniqueId())) {
						DogeFeatures.banWantedPlayers.get(x).Voted.remove(y);
						p.sendMessage("已取消封禁投票");
						return true;
					}
					y++;
				}
				int banNeededPlayers = DogeFeatures.getPlugin().getConfig().getInt("voteSettings.banNeededPlayers");
				DogeFeatures.banWantedPlayers.get(x).Voted.add(p.getUniqueId());
				Bukkit.broadcastMessage(ChatColor.RED + "[封禁投票]" + ChatColor.WHITE + " 对玩家 " + ChatColor.YELLOW + targetPlayerName + " 的封禁投票数: " + ChatColor.DARK_RED + String.valueOf(pObj.Voted.size()) + "/" + String.valueOf(banNeededPlayers));
				p.sendMessage("投票成功");
				
				if(pObj.Voted.size() >= banNeededPlayers) {
					//ban a player
					OfflinePlayer targetPlayerObj = DogeFeatures.getPlugin().getServer().getOfflinePlayer(pObj.Player);
					Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetPlayerObj.getName(), "Banned by vote plugin." , null, pObj.Creater.getName());
					if(targetPlayerObj.isOnline()) {
						DogeFeatures.getPlugin().getServer().getPlayer(pObj.Player).kickPlayer("You are banned from this server.");
					}
					Bukkit.broadcastMessage(ChatColor.RED + "[封禁投票] " + ChatColor.YELLOW + targetPlayerObj.getName() + ChatColor.RED + "已被封禁");
					DogeFeatures.banWantedPlayers.remove(x);
				}
				return true;
			}
			x++;
		}
		
		p.sendMessage("无法投票. 该玩家没有被发起投票.");
		return true;
	}
}
