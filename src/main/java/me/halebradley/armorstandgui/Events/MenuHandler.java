package me.halebradley.armorstandgui.Events;

import me.halebradley.armorstandgui.ArmorStandGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuHandler implements Listener {

    ArmorStandGUI plugin;

    public MenuHandler(ArmorStandGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        //Menus
        final String MAIN_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("main-menu"));
        final String CREATE_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("create-menu"));
        final String CONFIRMATION_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirmation-menu"));
        final String ARMOR_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("armor-menu"));

         if(e.getView().getTitle().equalsIgnoreCase(MAIN_MENU)) {
                switch (e.getCurrentItem().getType()) {
                     case ARMOR_STAND -> {
                         p.closeInventory();
                         plugin.openCreateMenu(p);
                     }
                     case BARRIER -> {
                         p.closeInventory();
                     }
                }
                e.setCancelled(true);
         } else if(e.getView().getTitle().equalsIgnoreCase(CREATE_MENU)) {
             if(!plugin.armorStands.containsKey(p)) {
                 ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                 stand.setVisible(false);
                 plugin.armorStands.put(p, stand);
             }
             switch (e.getCurrentItem().getType()) {
                 case STICK -> {
                     plugin.openConfirmationMenu(p, Material.STICK);
                 }
                 case BEACON -> {
                     plugin.openConfirmationMenu(p, Material.BEACON);
                 }
                 case LEATHER_CHESTPLATE -> {
                     plugin.openArmorMenu(p);
                 }
                 case STONE_SLAB -> {
                     plugin.openConfirmationMenu(p, Material.STONE_SLAB);
                 }
                 case GREEN_WOOL -> {
                     p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("create-message")));
                     ArmorStand stand = plugin.armorStands.get(p);
                     stand.setVisible(true);
                     plugin.armorStands.remove(p);
                     p.closeInventory();
                 }
                 case RED_WOOL -> {
                     p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("delete-message")));
                     ArmorStand stand = plugin.armorStands.get(p);
                     stand.remove();
                     plugin.armorStands.remove(p);
                     p.closeInventory();
                 }
             }
             e.setCancelled(true);
         } else if(e.getView().getTitle().equalsIgnoreCase(CONFIRMATION_MENU)){
             if(e.getClickedInventory().contains(Material.STICK)){
                 switch (e.getCurrentItem().getType()) {
                     case GREEN_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setArms(true);
                         plugin.openCreateMenu(p);
                     }
                     case RED_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("deny-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setArms(false);
                         plugin.openCreateMenu(p);
                     }
                 }
             } else if(e.getClickedInventory().contains(Material.BEACON)){
                 switch (e.getCurrentItem().getType()) {
                     case GREEN_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setGlowing(true);
                         plugin.openCreateMenu(p);
                     }
                     case RED_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("deny-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setGlowing(false);
                         plugin.openCreateMenu(p);
                     }
                 }
             } else if(e.getClickedInventory().contains(Material.STONE_SLAB)){
                 switch (e.getCurrentItem().getType()) {
                     case GREEN_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setBasePlate(true);
                         plugin.openCreateMenu(p);
                     }
                     case RED_WOOL -> {
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("deny-option")));
                         ArmorStand stand = plugin.armorStands.get(p);
                         stand.setBasePlate(false);
                         plugin.openCreateMenu(p);
                     }
                 }
             }
             e.setCancelled(true);
         } else if (e.getView().getTitle().equalsIgnoreCase(ARMOR_MENU)){
             ArmorStand stand = plugin.armorStands.get(p);
             switch (e.getCurrentItem().getType()) {
                 //LEATHER
                 case LEATHER_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.LEATHER_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
                     }
                 }
                 case LEATHER_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.LEATHER_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                     }
                 }
                 case LEATHER_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.LEATHER_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                     }
                 }
                 case LEATHER_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.LEATHER_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
                     }
                 }
                 //IRON
                 case IRON_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.IRON_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
                     }
                 }
                 case IRON_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.IRON_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
                     }
                 }
                 case IRON_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.IRON_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
                     }
                 }
                 case IRON_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.IRON_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
                     }
                 }
                 //GOLDEN
                 case GOLDEN_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
                     }
                 }
                 case GOLDEN_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.GOLDEN_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
                     }
                 }
                 case GOLDEN_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.GOLDEN_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS, 1));
                     }
                 }
                 case GOLDEN_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.GOLDEN_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS, 1));
                     }
                 }
                 //CHAINMAIL
                 case CHAINMAIL_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.CHAINMAIL_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
                     }
                 }
                 case CHAINMAIL_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                     }
                 }
                 case CHAINMAIL_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
                     }
                 }
                 case CHAINMAIL_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.CHAINMAIL_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                     }
                 }

                 //DIAMOND
                 case DIAMOND_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.DIAMOND_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
                     }
                 }
                 case DIAMOND_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.DIAMOND_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                     }
                 }
                 case DIAMOND_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.DIAMOND_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
                     }
                 }
                 case DIAMOND_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.DIAMOND_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
                     }
                 }
                 //NETHERITE
                 case NETHERITE_HELMET -> {
                     if(stand.getEquipment().getHelmet().getType() == Material.NETHERITE_HELMET){
                         stand.getEquipment().setHelmet(null);
                     } else {
                         stand.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET, 1));
                     }
                 }
                 case NETHERITE_CHESTPLATE -> {
                     if(stand.getEquipment().getChestplate().getType() == Material.NETHERITE_CHESTPLATE){
                         stand.getEquipment().setChestplate(null);
                     } else {
                         stand.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE, 1));
                     }
                 }
                 case NETHERITE_LEGGINGS -> {
                     if(stand.getEquipment().getLeggings().getType() == Material.NETHERITE_LEGGINGS){
                         stand.getEquipment().setLeggings(null);
                     } else {
                         stand.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS, 1));
                     }
                 }
                 case NETHERITE_BOOTS -> {
                     if(stand.getEquipment().getBoots().getType() == Material.NETHERITE_BOOTS){
                         stand.getEquipment().setBoots(null);
                     } else {
                         stand.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS, 1));
                     }
                 }
                 case GREEN_WOOL -> {
                     plugin.openCreateMenu(p);
                 }
             }
             e.setCancelled(true);
         }
    }
}
