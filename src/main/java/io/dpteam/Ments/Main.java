package io.dpteam.Ments;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public FileConfiguration config;

	public Main() {
		super();
	}

	public void onEnable() {
		this.config = this.getConfig();
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getLogger().info("[Ments] Plugin loaded and enabled");
	}

	@Override
	public void onDisable() {
		this.getServer().getLogger().info("[Ments] Plugin unloaded and disabled");
	}

	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Entity player = e.getEntity().getKiller();
		this.reloadConfig();
		if (player instanceof Player && !((Player)player).hasPermission("ments.bypass")) {
			Location inFront = player.getLocation().toVector().add(player.getLocation().getDirection().multiply(4)).toLocation(player.getWorld());
			int x = inFront.getBlockX();
			int y = player.getLocation().getBlockY();
			int z = inFront.getBlockZ();
			Location finalLocation = new Location(player.getWorld(), (double)x, (double)y, (double)z);
			PigZombie ment = (PigZombie)player.getWorld().spawn(finalLocation, PigZombie.class);
			ment.setTarget(e.getEntity().getKiller());
			ment.setCustomName(this.getConfig().getString("nameplate").replace("&", "ยง").replace("%d%", e.getEntity().getName()).replace("%k%", e.getEntity().getKiller().getName()));
			ment.setCustomNameVisible(true);
			ment.setBaby(false);
			EntityEquipment eq = ment.getEquipment();
			ItemStack boots = new ItemStack(Material.valueOf(this.getConfig().getString("boots")), 1);
			ItemStack pants = new ItemStack(Material.valueOf(this.getConfig().getString("leggings")), 1);
			ItemStack chestplate = new ItemStack(Material.valueOf(this.getConfig().getString("chestplate")), 1);
			ItemStack helmet = new ItemStack(Material.valueOf(this.getConfig().getString("helmet")), 1);
			ItemStack weapon = new ItemStack(Material.valueOf(this.getConfig().getString("weapon")), 1);
			eq.setBoots(boots);
			eq.setLeggings(pants);
			eq.setChestplate(chestplate);
			eq.setHelmet(helmet);
			eq.setItemInHand(weapon);
			eq.setItemInHandDropChance(0.0F);
			eq.setChestplateDropChance(0.0F);
			eq.setBootsDropChance(0.0F);
			eq.setHelmetDropChance(0.0F);
			eq.setLeggingsDropChance(0.0F);
		}
	}
}
