package me.gabriel.pvptoggle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.gabriel.pvptoggle.commands.PvP;
import me.gabriel.pvptoggle.listeners.CombatListener;
import me.gabriel.pvptoggle.util.CombatUtil;

public class PvPToggleMain extends JavaPlugin {

	private static PvPToggleMain instance;
	
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new CombatListener(), this);
		this.getCommand("pvp").setExecutor(new PvP());
		System.out.println(CombatUtil.getPrefix() + "PvPToggle Enabled");
	}
	
	public static PvPToggleMain getInstance() {
		return instance;
	}
	
}
