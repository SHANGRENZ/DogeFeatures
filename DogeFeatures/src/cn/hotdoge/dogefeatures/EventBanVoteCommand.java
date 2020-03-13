package cn.hotdoge.dogefeatures;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class EventBanVoteCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		String targetPlayerName;
		try {
			targetPlayerName = args[0];
		} catch (Exception e) {
			p.sendMessage("�÷�:/banvote player ͶƱ/ȡ��ͶƱ���ĳ�����");
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
						p.sendMessage("��ȡ�����ͶƱ");
						return true;
					}
					y++;
				}
				int banNeededPlayers = DogeFeatures.getPlugin().getConfig().getInt("voteSettings.banNeededPlayers");
				DogeFeatures.banWantedPlayers.get(x).Voted.add(p.getUniqueId());
				Bukkit.broadcastMessage(ChatColor.RED + "[���ͶƱ]" + ChatColor.WHITE + " ����� " + ChatColor.YELLOW + targetPlayerName + " �ķ��ͶƱ��: " + ChatColor.DARK_RED + String.valueOf(pObj.Voted.size()) + "/" + String.valueOf(banNeededPlayers));
				p.sendMessage("ͶƱ�ɹ�");
				
				if(pObj.Voted.size() >= banNeededPlayers) {
					//ban a player
					OfflinePlayer targetPlayerObj = DogeFeatures.getPlugin().getServer().getOfflinePlayer(pObj.Player);
					Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetPlayerObj.getName(), "Banned by vote plugin." , null, pObj.Creater.getName());
					if(targetPlayerObj.isOnline()) {
						DogeFeatures.getPlugin().getServer().getPlayer(pObj.Player).kickPlayer("You are banned from this server.");
					}
					Bukkit.broadcastMessage(ChatColor.RED + "[���ͶƱ] " + ChatColor.YELLOW + targetPlayerObj.getName() + ChatColor.RED + "�ѱ����");
					DogeFeatures.banWantedPlayers.remove(x);
				}
				return true;
			}
			x++;
		}
		
		p.sendMessage("�޷�ͶƱ. �����û�б�����ͶƱ.");
		return true;
	}
}
