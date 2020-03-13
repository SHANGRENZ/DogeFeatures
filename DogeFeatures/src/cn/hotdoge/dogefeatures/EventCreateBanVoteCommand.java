package cn.hotdoge.dogefeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EventCreateBanVoteCommand extends JavaPlugin implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		String targetPlayerName;
		try {
			targetPlayerName = args[0];
		} catch (Exception e) {
			p.sendMessage("用法:/banvotecreate player 发起/删除一个封禁投票");
			return true;
		}
		UUID targetPlayerUuid = this.getServer().getOfflinePlayer(targetPlayerName).getUniqueId();
		
		//remove ban request
		for(BanWantedPlayersObj pObj:DogeFeatures.banWantedPlayers) {
			if(pObj.Player.equals(targetPlayerUuid)) {
				if(!pObj.Creater.equals(p)) {
					p.sendMessage("无法发起, 该玩家已有一个正在进行中的投票. 发起者: " + pObj.Creater.getName());
					return true;
				}
				DogeFeatures.banWantedPlayers.remove(pObj);
				p.sendMessage("已取消对玩家 " + targetPlayerName + " 的封禁投票");
				Bukkit.broadcastMessage(ChatColor.GREEN + "[封禁投票] 对玩家" + ChatColor.YELLOW + targetPlayerName + ChatColor.GREEN + "的封禁投票取消了");
				return true;
			}
		}
		
		DogeFeatures.banWantedPlayers.add(new BanWantedPlayersObj(targetPlayerUuid, new ArrayList<UUID>(), p));
		p.sendMessage("已发起封禁玩家 " + targetPlayerName + " 的封禁投票");
		Bukkit.broadcastMessage(ChatColor.RED + "[封禁投票] 有人发起了对玩家" + ChatColor.YELLOW + targetPlayerName + ChatColor.RED + "的封禁投票");
		
		return true;
	}
}
