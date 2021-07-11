package me.gabriel.pvptoggle.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import me.gabriel.pvptoggle.PvPToggleMain;
import net.md_5.bungee.api.ChatColor;

public class CombatUtil {

	public static NamespacedKey getPvPKey() {
		return new NamespacedKey(PvPToggleMain.getInstance(), "pvp");
	}

	public static boolean getPvPToggleState(Player player) {
		return Boolean.parseBoolean(
				player.getPersistentDataContainer().getOrDefault(getPvPKey(), PersistentDataType.STRING, "false"));
	}

	public static void setPvPToggleState(Player player, boolean bool) {
		player.getPersistentDataContainer().set(getPvPKey(), PersistentDataType.STRING, Boolean.toString(bool));
	}

	public static String getPrefix() {
		return ChatColor.BLUE + "[" + ChatColor.GRAY + "PvPToggle" + ChatColor.BLUE + "] ";
	}

}
