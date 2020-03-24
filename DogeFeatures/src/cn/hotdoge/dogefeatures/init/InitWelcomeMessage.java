package cn.hotdoge.dogefeatures.init;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;

import cn.hotdoge.dogefeatures.DogeFeatures;
import cn.hotdoge.dogefeatures.methods.CommonMethods;
import cn.hotdoge.dogefeatures.vars.VarWelcomeMessage;

public class InitWelcomeMessage {

	public InitWelcomeMessage() {
		VarWelcomeMessage.playersWhoCannotMove = new ArrayList<UUID>();
		VarWelcomeMessage.welcomeMessageTitleString = CommonMethods.getColorfulText(DogeFeatures.getPlugin().getConfig().getString("welcomeMessage.title"));
		VarWelcomeMessage.welcomeMessageSubTitleString = CommonMethods.getColorfulText(DogeFeatures.getPlugin().getConfig().getString("welcomeMessage.subTitle"));
	}
}
