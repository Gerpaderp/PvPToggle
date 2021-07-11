package me.gabriel.pvptoggle.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gabriel.pvptoggle.util.CombatUtil;
import net.md_5.bungee.api.ChatColor;

public class PvP implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		String prefix = CombatUtil.getPrefix();

		if (!(sender.hasPermission("pvptoggle.toggle"))) {
			sender.sendMessage(prefix + ChatColor.RED + "You lack permission to use this command.");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + ChatColor.RED + "This command must be used by a player");
			return true;
		}

		String usage = prefix + ChatColor.RED + "usage: /pvp <on|off>";
		Player player = (Player) sender;

		if (!(args.length == 1 || args.length == 2)) {
			player.sendMessage(usage);
			return true;
		}

		String[] options = { "on", "off", "toggle" };

		if (!(Arrays.asList(options)).contains(args[0].toLowerCase())) {
			player.sendMessage(usage);
			return true;
		}

		if (args.length == 2 && !player.hasPermission("pvptoggle.others")) {
			player.sendMessage(prefix + ChatColor.RED + "You lack permission to toggle others' pvp");
			return true;
		}

		Player target = args.length == 1 ? player : Bukkit.getPlayer(args[1]);

		if (target == null || !target.isOnline()) {
			player.sendMessage(prefix + ChatColor.RED + "Could not find the player: " + args[1]);
			return true;
		}

		boolean state;

		if (args[0].equalsIgnoreCase("toggle")) {
			state = !CombatUtil.getPvPToggleState(target);
		} else {
			state = args[0].equalsIgnoreCase("on");
		}

		CombatUtil.setPvPToggleState(target, state);

		String status = state ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

		target.sendMessage(prefix + ChatColor.GRAY + "Your PvP has been " + status);

		if (!(player.equals(target))) {
			player.sendMessage(prefix + ChatColor.GRAY + target.getName() + "'s PvP has been " + status);
		}

		return true;
	}

}
