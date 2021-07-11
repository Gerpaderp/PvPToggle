package me.gabriel.pvptoggle.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.gabriel.pvptoggle.util.CombatUtil;
import net.md_5.bungee.api.ChatColor;

public class CombatListener implements Listener {

	@EventHandler
	public void onCombat(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();

		var source = event.getDamager() instanceof Projectile ? ((Projectile) event.getDamager()).getShooter()
				: event.getDamager();

		if (!(source instanceof Player)) {
			return;
		}

		Player damager = (Player) source;

		if (CombatUtil.getPvPToggleState(player) && CombatUtil.getPvPToggleState(damager)) {
			return;
		}

		event.setCancelled(true);
		String message = CombatUtil.getPvPToggleState(damager)
				? player.getName() + " has their pvp " + ChatColor.RED + "disabled"
				: "You must enable PvP to attack this player";
		damager.sendMessage(CombatUtil.getPrefix() + message);

	}

}
