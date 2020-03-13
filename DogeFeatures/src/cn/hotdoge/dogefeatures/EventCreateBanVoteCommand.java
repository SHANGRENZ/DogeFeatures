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
			p.sendMessage("�÷�:/banvotecreate player ����/ɾ��һ�����ͶƱ");
			return true;
		}
		UUID targetPlayerUuid = this.getServer().getOfflinePlayer(targetPlayerName).getUniqueId();
		
		//remove ban request
		for(BanWantedPlayersObj pObj:DogeFeatures.banWantedPlayers) {
			if(pObj.Player.equals(targetPlayerUuid)) {
				if(!pObj.Creater.equals(p)) {
					p.sendMessage("�޷�����, ���������һ�����ڽ����е�ͶƱ. ������: " + pObj.Creater.getName());
					return true;
				}
				DogeFeatures.banWantedPlayers.remove(pObj);
				p.sendMessage("��ȡ������� " + targetPlayerName + " �ķ��ͶƱ");
				Bukkit.broadcastMessage(ChatColor.GREEN + "[���ͶƱ] �����" + ChatColor.YELLOW + targetPlayerName + ChatColor.GREEN + "�ķ��ͶƱȡ����");
				return true;
			}
		}
		
		DogeFeatures.banWantedPlayers.add(new BanWantedPlayersObj(targetPlayerUuid, new ArrayList<UUID>(), p));
		p.sendMessage("�ѷ�������� " + targetPlayerName + " �ķ��ͶƱ");
		Bukkit.broadcastMessage(ChatColor.RED + "[���ͶƱ] ���˷����˶����" + ChatColor.YELLOW + targetPlayerName + ChatColor.RED + "�ķ��ͶƱ");
		
		return true;
	}
}
