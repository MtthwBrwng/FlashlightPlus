package com.fallenyouth.FlashlightPlus.listeners;

import com.fallenyouth.FlashlightPlus.FlashlightPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Made by FallenYouth
 */

public class EventListener implements Listener {
    //Removes night vision effect
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();

        if (FlashlightPlus.getFlashLightToggle().contains(player.getName())) {
            FlashlightPlus.getFlashLightToggle().remove(player.getName());
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
    //Enables flashlight when right clicking a torch
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack torch = player.getItemInHand();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) &&
                (torch.hasItemMeta()) &&
                (torch.getItemMeta().getDisplayName() != null) &&
                (torch.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "[" + ChatColor.WHITE + "Flashlight" + ChatColor.DARK_AQUA + "]"))) {
            FlashlightPlus.togglePlayer(player);
        }
    }
    //Cancels placing of "Flashlight" torch
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack torch = player.getItemInHand();
        if (FlashlightPlus.addToCooldown(player)) return;
        if (block.getType() == Material.TORCH && torch.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "[" + ChatColor.WHITE + "Flashlight" + ChatColor.DARK_AQUA + "]")) {
            player.sendMessage(FlashlightPlus.getMessage(ChatColor.RED + "You are not allowed to place this block!"));
            event.setCancelled(true);
        }
    }
}