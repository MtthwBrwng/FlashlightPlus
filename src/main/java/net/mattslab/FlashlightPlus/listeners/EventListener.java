package net.mattslab.FlashlightPlus.listeners;

import net.mattslab.FlashlightPlus.FlashlightPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Made by Matt
 */

public class EventListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();

        if (FlashlightPlus.getFlashLightToggle().contains(player.getName())) {
            FlashlightPlus.getFlashLightToggle().remove(player.getName());
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

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

    @EventHandler
    public void onPlayerPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack torch = player.getItemInHand();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                (torch.hasItemMeta()) &&
                (torch.getItemMeta().getDisplayName() != null) &&
                (torch.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "[" + ChatColor.WHITE + "Flashlight" + ChatColor.DARK_AQUA + "]"))) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDrinkMilk(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack milk = new ItemStack(event.getItem());
        if (milk.getType().equals(Material.MILK_BUCKET) && FlashlightPlus.getFlashLightToggle().contains(player.getName())) {
            event.setCancelled(true);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (!(player.getActivePotionEffects() == PotionEffectType.NIGHT_VISION)) {
                    player.removePotionEffect(effect.getType());
                }
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true));
        }
    }
}