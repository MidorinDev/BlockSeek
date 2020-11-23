package plugin.midorin.info.bs.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import plugin.midorin.info.bs.util.Messages;

public class Mode implements CommandExecutor
{
    public static boolean easy = false;
    public static boolean normal = false;
    public static boolean hard = false;

    public static Inventory gui = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "GameMode Selector");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        try
        {
            if (args[0].equalsIgnoreCase("easy"))
            {
                if (sender.hasPermission("BlockSeek.mode.easy"))
                {
                    if (!easy)
                    {
                        if (normal) normal = false;
                        else if (hard) hard = false;
                        easy = true;
                        Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "イージー" + ChatColor.GREEN + " に変更されました。");
                    }
                    else
                        p.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "イージー" + ChatColor.RED + " です。");
                }
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("normal"))
            {
                if (sender.hasPermission("BlockSeek.mode.normal"))
                {
                    if (Mode.normal)
                    {
                        if (easy) easy = false;
                        else if (hard) hard = false;
                        normal = true;
                        Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "ノーマル" + ChatColor.GREEN + " に変更されました。");
                    }
                    else
                        p.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "ノーマル" + ChatColor.RED + " です。");
                }
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("hard"))
            {
                if (sender.hasPermission("BlockSeek.mode.hard"))
                {
                    if (hard)
                    {
                        if (easy) easy = false;
                        else if (normal) normal = false;
                        hard = true;
                        Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "ハード" + ChatColor.GREEN + " に変更されました。");
                    }
                    else
                        p.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "ハード" + ChatColor.RED + " です。");
                }
                else
                    Messages.sendPermissionError(sender);
            }
        }
        catch (Exception ex)
        {
            if (sender.hasPermission("BlockSeek.mode"))
                p.openInventory(gui);
            else
                Messages.sendPermissionError(sender);
        }
        return true;
    }
}
