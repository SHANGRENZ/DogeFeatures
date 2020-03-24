package cn.hotdoge.dogefeatures.methods;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class CommonMethods {
	
	public static List<String> getStringList(String str, Integer len){
		List<String> strList = new ArrayList<String>();
		int strLen = str.length();
		int start = 0;
		int end = len;
		String temp = null;
		while (true) {
			try {
				if(end <= strLen) {
					temp = str.substring(start, end);
				}else {
					temp = str.substring(start, strLen);
				}
			} catch (Exception e) {
				break;
			}
			strList.add(temp);
			start = end;
			end = end + len;
		}
		return strList;
	}
	
	public static String getColorfulText(String line) {
		line = line.replaceAll("&0", ChatColor.BLACK + "");
        line = line.replaceAll("&1", ChatColor.DARK_BLUE + "");
        line = line.replaceAll("&2", ChatColor.DARK_GREEN + "");
        line = line.replaceAll("&3", ChatColor.DARK_AQUA + "");
        line = line.replaceAll("&4", ChatColor.DARK_RED + "");
        line = line.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        line = line.replaceAll("&6", ChatColor.GOLD + "");
        line = line.replaceAll("&7", ChatColor.GRAY + "");
        line = line.replaceAll("&8", ChatColor.DARK_GRAY+ "");
        line = line.replaceAll("&9", ChatColor.BLUE + "");
        line = line.replaceAll("&a", ChatColor.GREEN + "");
        line = line.replaceAll("&b", ChatColor.AQUA + "");
        line = line.replaceAll("&c", ChatColor.RED + "");
        line = line.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        line = line.replaceAll("&e", ChatColor.YELLOW + "");
        line = line.replaceAll("&f", ChatColor.WHITE + "");
        line = line.replaceAll("&g", ChatColor.MAGIC + "");
        
        return line;
	}
}
