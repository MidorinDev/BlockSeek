package plugin.midorin.info.bs.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import plugin.midorin.info.bs.Game;
import plugin.midorin.info.bs.Items;
import plugin.midorin.info.bs.Teams.Entrant;
import plugin.midorin.info.bs.util.Messages;

public class Main implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        try
        {
            if (args[0].equalsIgnoreCase("help"))
            {
                if (args[1].equalsIgnoreCase("1"))
                    Game.help1(sender);
                else if (args[1].equalsIgnoreCase("2"))
                    Game.help2(sender);
                else
                    Game.help1(sender);
            }
            else if (args[0].equalsIgnoreCase("start"))
            {
                if (sender.hasPermission("BlockSeek.start"))
                {
                    if (!(Mode.easy || Mode.normal || Mode.hard))
                    {
                        for (Player ap : Bukkit.getOnlinePlayers())
                        {
                            if (!(ap.getGameMode() == GameMode.CREATIVE || ap.getGameMode() == GameMode.SPECTATOR))
                                Entrant.addPlayer(ap, false);
                        }
                        Game.start();
                    }
                    else
                        sender.sendMessage(Messages.PREFIX + ChatColor.RED + "ゲームの難易度を設定してください。");
                }
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("finish"))
            {
                if (sender.hasPermission("BlockSeek.finish"))
                    Game.finish();
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("setblock"))
            {
                if (sender.hasPermission("BlockSeek.setblock"))
                {
                    if (Game.select_block != null)
                    {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (item != null)
                        {
                            Game.select_block.add(item.getType());
                            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.YELLOW + "指定ブロックを " + ChatColor.WHITE + Game.select_block.get(0) + ChatColor.YELLOW + " ブロックに設定しました。");
                            Items.setBlock();
                        }
                        else
                            sender.sendMessage(Messages.PREFIX + ChatColor.RED + "指定ブロックを手に持って実行してください。");
                    }
                    else
                        sender.sendMessage(Messages.PREFIX + ChatColor.RED + "指定ブロックは常に選択されています。");
                }
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("range"))
            {
                if (sender.hasPermission("Blockseek.range"))
                {
                    if (args.length == 1)
                        sender.sendMessage(Messages.PREFIX + ChatColor.RED + "マップの範囲を指定してください。");
                    else
                    {
                        if (!StringUtils.isNumeric(args[1])) sender.sendMessage(Messages.PREFIX + ChatColor.RED + "数字以外は設定できません。");
                        else if (Integer.parseInt(args[1]) <= 0) sender.sendMessage(Messages.PREFIX + ChatColor.RED + "0未満は設定できません。");
                        else
                        {
                            Bukkit.getWorld(p.getWorld().getName()).getWorldBorder().setCenter(p.getLocation().getX(), p.getLocation().getZ());
                            Bukkit.getWorld(p.getWorld().getName()).getWorldBorder().setSize(Integer.parseInt(args[1]));
                            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "マップの範囲が" + ChatColor.WHITE + Integer.valueOf(args[1]) + "ブロック" + ChatColor.GREEN + "に変更されました。");
                            for (Player ap : Bukkit.getOnlinePlayers()) ap.teleport(p.getLocation().clone());
                        }
                    }
                }
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("spawnpoint"))
            {
                if (sender.hasPermission("spawn"))
                    sender.sendMessage(Messages.PREFIX + ChatColor.RED + "現在この機能はご利用になれません。");
                else
                    Messages.sendPermissionError(sender);
            }
            else if (args[0].equalsIgnoreCase("reset"))
            {
                if (sender.hasPermission("BlockSeek.reset"))
                    Game.reset();
                else
                    Messages.sendPermissionError(sender);
            }
        }
        catch (Exception ex)
        {
            Game.help1(sender);
        }
        return true;
    }
}
