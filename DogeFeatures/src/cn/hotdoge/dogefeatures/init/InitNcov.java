package cn.hotdoge.dogefeatures.init;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.vars.VarNcov;

public class InitNcov {
	
	public InitNcov() {
		VarNcov.ncovIsEnabled = DogeFeatures.getPlugin().getConfig().getBoolean("featureSettings.coronaVirus");
		VarNcov.ncovBossBar = Bukkit.createBossBar("Coronavirus", BarColor.RED, BarStyle.SEGMENTED_6, BarFlag.DARKEN_SKY);
		VarNcov.ncovWhoHasArrayList = new ArrayList<UUID>();
		System.out.println("ncov enabled!");
	}
	
}
