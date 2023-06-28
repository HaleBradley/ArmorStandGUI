package me.halebradley.armorstandgui.Commands;

import me.halebradley.armorstandgui.ArmorStandGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ArmorStandCommand implements CommandExecutor {

    ArmorStandGUI plugin;

    public ArmorStandCommand(ArmorStandGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player p) {
            plugin.openMainMenu(p);
        }

        return true;
    }
}
