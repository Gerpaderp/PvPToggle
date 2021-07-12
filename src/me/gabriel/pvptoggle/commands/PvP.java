package me.gabriel.pvptoggle.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.gabriel.pvptoggle.util.CombatUtil.*;
import net.md_5.bungee.api.ChatColor;

public class PvP implements CommandExecutor {
	
	private static final Set<String> options = Set.of("on", "off", "toggle");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sendMessage(sender, ChatColor.RED + "This command must be used by a player");
			return true;
		}
		
		if (!(sender.hasPermission("pvptoggle.toggle"))) {
			sendMessage(sender, "You lack permission to use this command");
			return true;
		}

		String usage = ChatColor.RED + "usage: /pvp <on|off>";
		Player player = (Player) sender;

		if (!(args.length == 1 || args.length == 2)) {
			sendMessage(player, usage);
			return true;
		}

		if (!options.contains(args[0].toLowerCase())) {
			player.sendMessage(usage);
			return true;
		}

		if (args.length == 2 && !player.hasPermission("pvptoggle.others")) {
			sendMessage(player, ChatColor.RED + "You lack permission to toggle others' pvp");
			return true;
		}

		Player target = args.length == 1 ? player : Bukkit.getPlayer(args[1]);

		if (target == null || !target.isOnline()) {
			sendMessage(player, ChatColor.RED + "Could not find the player: " + args[1]);
			return true;
		}

		boolean state;

		if (args[0].equalsIgnoreCase("toggle")) {
			state = !getPvPToggleState(target);
		} else {
			state = args[0].equalsIgnoreCase("on");
		}

		setPvPToggleState(target, state);

		String status = state ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";

		sendMessage(target, ChatColor.GRAY + "Your PvP has been " + status);

		if (!(player.equals(target))) {
			sendMessage(player, ChatColor.GRAY + target.getName() + "'s PvP has been " + status);
		}

		return true;
	}

}
