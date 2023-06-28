package me.halebradley.armorstandgui;

import me.halebradley.armorstandgui.Commands.ArmorStandCommand;
import me.halebradley.armorstandgui.Events.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class ArmorStandGUI extends JavaPlugin {

    public HashMap<Player, ArmorStand> armorStands = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("ArmorStand GUI Started");
        //Commands
        getCommand("armorstand").setExecutor(new ArmorStandCommand(this));
        //Events
        getServer().getPluginManager().registerEvents(new MenuHandler(this), this);
        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void openMainMenu(Player p){
        Inventory mainMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("main-menu")));

        //  Menu Options
        //1: Create an armor stand
        ItemStack create = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta createMeta = create.getItemMeta();
        createMeta.setDisplayName(ChatColor.GREEN + "Create");
        ArrayList<String> createLore = new ArrayList<>();
        createLore.add("Create a new armor stand.");
        createMeta.setLore(createLore);
        create.setItemMeta(createMeta);

        //2: Close the armor stand GUI menu
        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.GREEN + "Close");
        ArrayList<String> closeLore = new ArrayList<>();
        closeLore.add("Close this menu.");
        closeMeta.setLore(closeLore);
        close.setItemMeta(closeMeta);

        mainMenu.setItem(0, create);
        mainMenu.setItem(8, close);
        p.openInventory(mainMenu);
    }

    public void openCreateMenu(Player p) {
        Inventory createMenu = Bukkit.createInventory(p, 9, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("create-menu")));

        //  Menu Options
        //1: Create an armor stand
        ItemStack arms = new ItemStack(Material.STICK, 1);
        ItemMeta armsMeta = arms.getItemMeta();
        armsMeta.setDisplayName(ChatColor.GREEN + "Arms");
        ArrayList<String> armsLore = new ArrayList<>();
        armsLore.add("Give the custom armor stand arms.");
        armsMeta.setLore(armsLore);
        arms.setItemMeta(armsMeta);

        //2: Give custom armor stand glow
        ItemStack glow = new ItemStack(Material.BEACON, 1);
        ItemMeta glowMeta = glow.getItemMeta();
        glowMeta.setDisplayName(ChatColor.GREEN + "Glow");
        ArrayList<String> glowLore = new ArrayList<>();
        glowLore.add("Give the custom armor stand a glow.");
        glowMeta.setLore(glowLore);
        glow.setItemMeta(glowMeta);

        //3: Choose the armor type for the stand
        ItemStack armor = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta armorMeta = armor.getItemMeta();
        armorMeta.setDisplayName(ChatColor.GREEN + "Armor");
        ArrayList<String> armorLore = new ArrayList<>();
        armorLore.add("Select what armor will be on the stand.");
        armorMeta.setLore(armorLore);
        armor.setItemMeta(armorMeta);

        //4: Choose if armor stand has a base or not
        ItemStack base = new ItemStack(Material.STONE_SLAB, 1);
        ItemMeta baseMeta = base.getItemMeta();
        baseMeta.setDisplayName(ChatColor.GREEN + "Base");
        ArrayList<String> baseLore = new ArrayList<>();
        baseLore.add("Give the custom armor stand a base.");
        baseMeta.setLore(baseLore);
        base.setItemMeta(baseMeta);

        //5: Create the custom armor stand
        ItemStack complete = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta completeMeta = complete.getItemMeta();
        completeMeta.setDisplayName(ChatColor.GREEN + "Complete");
        ArrayList<String> completeLore = new ArrayList<>();
        completeLore.add("Create the custom armor stand.");
        completeMeta.setLore(completeLore);
        complete.setItemMeta(completeMeta);

        //5: Cancel the creation of a custom armor stand
        ItemStack cancel = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.GREEN + "Cancel");
        ArrayList<String> cancelLore = new ArrayList<>();
        cancelLore.add("Cancel the creation and close this menu.");
        cancelMeta.setLore(cancelLore);
        cancel.setItemMeta(cancelMeta);

        createMenu.setItem(0, arms);
        createMenu.setItem(1, glow);
        createMenu.setItem(2, armor);
        createMenu.setItem(3, base);
        createMenu.setItem(7, complete);
        createMenu.setItem(8, cancel);
        p.openInventory(createMenu);
    }

    public void openConfirmationMenu(Player p, Material option) {
        Inventory confirmationMenu = Bukkit.createInventory(p, 36, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("confirmation-menu")));

        //  Menu Options
        //1: Item for the option selected in previous menu
        ItemStack optionItem = new ItemStack(option);
        ItemMeta optionMeta = optionItem.getItemMeta();
        if(option == Material.STICK) {
            optionMeta.setDisplayName(ChatColor.GREEN + "Give Arms?");
        } else if(option == Material.BEACON) {
            optionMeta.setDisplayName(ChatColor.GREEN + "Add Glow?");
        } else if(option == Material.STONE_SLAB){
            optionMeta.setDisplayName(ChatColor.GREEN + "Give Base?");
        }
        optionItem.setItemMeta(optionMeta);

        //2: Confirm the customization
        ItemStack confirm = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirmMeta);

        //3: Cancel the customization
        ItemStack deny = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.setDisplayName(ChatColor.GREEN + "Deny");
        deny.setItemMeta(denyMeta);

        confirmationMenu.setItem(13, optionItem);
        confirmationMenu.setItem(21, confirm);
        confirmationMenu.setItem(23, deny);
        p.openInventory(confirmationMenu);
    }

    public void openArmorMenu(Player p) {
        Inventory armorMenu = Bukkit.createInventory(p, 45, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("armor-menu")));

        //  Menu Items
        //1: Choose leather helmet
        ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET);

        //2: Choose leather chest plate
        ItemStack leatherChest = new ItemStack(Material.LEATHER_CHESTPLATE);

        //3: Choose leather leggings
        ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS);

        //4: Choose leather boots
        ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS);

        //5: Choose iron helmet
        ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET);

        //6: Choose iron chest plate
        ItemStack ironChest = new ItemStack(Material.IRON_CHESTPLATE);

        //7: Choose iron leggings
        ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS);

        //8: Choose iron boots
        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);

        //9: Choose gold helmet
        ItemStack goldHelmet = new ItemStack(Material.GOLDEN_HELMET);

        //10: Choose gold chest plate
        ItemStack goldChest = new ItemStack(Material.GOLDEN_CHESTPLATE);

        //11: Choose gold leggings
        ItemStack goldLeggings = new ItemStack(Material.GOLDEN_LEGGINGS);

        //12: Choose gold boots
        ItemStack goldBoots = new ItemStack(Material.GOLDEN_BOOTS);

        //13: Choose chainmail helmet
        ItemStack chainmailHelmet = new ItemStack(Material.CHAINMAIL_HELMET);

        //14: Choose chainmail chest plate
        ItemStack chainmailChest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);

        //15: Choose chainmail leggings
        ItemStack chainmailLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);

        //16: Choose chainmail boots
        ItemStack chainmailBoots = new ItemStack(Material.CHAINMAIL_BOOTS);

        //17: Choose diamond helmet
        ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);

        //18: Choose diamond chest plate
        ItemStack diamondChest = new ItemStack(Material.DIAMOND_CHESTPLATE);

        //19: Choose diamond leggings
        ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);

        //20: Choose diamond boots
        ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);

        //21: Choose netherite helmet
        ItemStack netheriteHelmet = new ItemStack(Material.NETHERITE_HELMET);

        //22: Choose netherite chest plate
        ItemStack netheriteChest = new ItemStack(Material.NETHERITE_CHESTPLATE);

        //23: Choose netherite leggings
        ItemStack netheriteLeggings = new ItemStack(Material.NETHERITE_LEGGINGS);

        //24: Choose netherite boots
        ItemStack netheriteBoots = new ItemStack(Material.NETHERITE_BOOTS);

        //??: Confirm armor choices
        ItemStack confirm = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirmMeta);

        //Leather armor
        armorMenu.setItem(1, leatherHelmet);
        armorMenu.setItem(10, leatherChest);
        armorMenu.setItem(19, leatherLeggings);
        armorMenu.setItem(28, leatherBoots);

        //Iron Armor
        armorMenu.setItem(2, ironHelmet);
        armorMenu.setItem(11, ironChest);
        armorMenu.setItem(20, ironLeggings);
        armorMenu.setItem(29, ironBoots);

        //Gold Armor
        armorMenu.setItem(3, goldHelmet);
        armorMenu.setItem(12, goldChest);
        armorMenu.setItem(21, goldLeggings);
        armorMenu.setItem(30, goldBoots);

        //Chainmail Armor
        armorMenu.setItem(5, chainmailHelmet);
        armorMenu.setItem(14, chainmailChest);
        armorMenu.setItem(23, chainmailLeggings);
        armorMenu.setItem(32, chainmailBoots);

        //Diamond Armor
        armorMenu.setItem(6, diamondHelmet);
        armorMenu.setItem(15, diamondChest);
        armorMenu.setItem(24, diamondLeggings);
        armorMenu.setItem(33, diamondBoots);

        //Netherite Armor
        armorMenu.setItem(7, netheriteHelmet);
        armorMenu.setItem(16, netheriteChest);
        armorMenu.setItem(25, netheriteLeggings);
        armorMenu.setItem(34, netheriteBoots);

        armorMenu.setItem(40, confirm);
        p.openInventory(armorMenu);
    }

}
