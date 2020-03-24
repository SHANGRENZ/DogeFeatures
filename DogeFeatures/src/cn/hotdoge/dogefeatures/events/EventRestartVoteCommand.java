package cn.hotdoge.dogefeatures;


import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.lang.model.element.VariableElement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.omg.CORBA.PUBLIC_MEMBER;

import net.minecraft.server.v1_15_R1.MinecraftServer;

public class EventRestartVoteCommand implements CommandExecutor {
	
	int restartTimeInt;
	int restartTaskId;
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("����������ҪͶƱ��ι!");
			return true;
		}
		
		Player p = (Player)sender;
		
		for(UUID uuidInList:DogeFeatures.votedPlayersRestart) {
			if(uuidInList.equals(p.getUniqueId())) {
				DogeFeatures.votedPlayersRestart.remove(uuidInList);
				p.sendMessage("��ȡ��ͶƱ");
				return true;
			}
		}
		DogeFeatures.votedPlayersRestart.add(p.getUniqueId());
		p.sendMessage("��ͶƱ");
		
		double sizeVoted = DogeFeatures.votedPlayersRestart.size();
		double neededPercent = DogeFeatures.getPlugin().getConfig().getDouble("voteSettings.restartPercent");
		double sizeAll = DogeFeatures.getPlugin().getServer().getOnlinePlayers().size();
		double tpsNow = MinecraftServer.getServer().recentTps[0];
		double tpsNeeded = DogeFeatures.getPlugin().getConfig().getDouble("voteSettings.restartTps");
		
		Bukkit.broadcastMessage(ChatColor.GREEN + "[����ͶƱ]" + ChatColor.WHITE + " ��С�ɰ�ϣ������������! " + ChatColor.YELLOW + String.valueOf((int)sizeVoted) + "/" + String.valueOf((int) Math.ceil(sizeAll * (neededPercent / 100))));
		if(sizeVoted / sizeAll * 100 >= neededPercent) {
			if(tpsNow > tpsNeeded) {
				Bukkit.broadcastMessage(ChatColor.GREEN + "[����ͶƱ]" + ChatColor.WHITE + " ��Ȼ��Ҷ�ͬ��, ����ֻ��tpsС��" + String.valueOf(tpsNeeded) + "ʱ��������Ӵ~");
			} else {
				Bukkit.broadcastMessage(ChatColor.GREEN + "[����ͶƱ]" + ChatColor.YELLOW + " ����������ʮ�������!");
				restartTimeInt = 5;
				Bukkit.getScheduler().runTaskTimer(DogeFeatures.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						Bukkit.broadcastMessage(ChatColor.YELLOW + "����������" + String.valueOf(restartTimeInt) + "�������.");
						restartTimeInt--;
						for(Player player:Bukkit.getOnlinePlayers()) {
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1F);
						}
						
						if(restartTimeInt <= 0) {
							Bukkit.getScheduler().cancelTask(restartTaskId);
							if(DogeFeatures.getPlugin().getConfig().getBoolean("voteSettings.useRestart")) {
								restartServer();
							} else {
								Bukkit.shutdown();
							}
						}
					}
				}, 100, 20);
			}
		}
		
		return true;
	}
	
	public void restartServer() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
				List<String> command = new ArrayList<String>();
				command.add("java");
				for(String cmd:args) {
					command.add(cmd);
				}
				command.add("-jar");
				command.add(new File(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath());
				try {
					new ProcessBuilder(command).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Bukkit.shutdown();
	}
}
