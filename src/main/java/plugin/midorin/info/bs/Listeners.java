package plugin.midorin.info.bs;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import plugin.midorin.info.bs.Teams.Entrant;
import plugin.midorin.info.bs.commands.Mode;
import plugin.midorin.info.bs.util.Messages;


public class Listeners implements Listener
{

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        Player p = event.getPlayer();

        if (Game.select_block == null) return;
        if (!BlockSeek.players.contains(p))
        {
            if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Game.select_block)
            {
                if (Mode.easy || Mode.normal)
                {
                    p.sendMessage(Messages.PREFIX + ChatColor.GOLD + "指定ブロックの上に乗りました。");
                    BlockSeek.players.add(p);
                }
                else if (Mode.hard)
                {
                    // 乗っていたらポイントを加算
                }
            }
        }
        else
        {
            BlockSeek.players.remove(p);
            p.sendMessage(Messages.PREFIX + ChatColor.RED + "指定ブロックの上から落ちました。");
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        Player p = e.getPlayer();
        if (Game.now)
        {
            if (Mode.easy)
            {
                p.setGameMode(GameMode.SPECTATOR);
                Entrant.removePlayer(p, false);
            }
            else if (Mode.normal)
            {
                // 死んだら幽霊になって生存者を妨害できる
            }
            else if (Mode.hard)
            {
                // 乗っていなかったら加算しない
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player clicker = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().equalsIgnoreCase(Mode.gui.getName()))
        {
            System.out.println("1");
            e.setCancelled(true);
            if (item.getType() == Material.MILK_BUCKET)
            {
                System.out.println("2");
                if (Mode.easy)
                {
                    clicker.closeInventory();
                    clicker.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "イージー" + ChatColor.RED + " です。");
                }
                else
                {
                    if (Mode.normal) Mode.normal = false;
                    else if (Mode.hard) Mode.hard = false;
                    Mode.easy = true;
                    clicker.closeInventory();
                    Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "イージー" + ChatColor.GREEN + " に変更されました。");
                }
            }
            else if (item.getType() == Material.WATER_BUCKET)
            {
                if (Mode.normal)
                {
                    clicker.closeInventory();
                    clicker.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "ノーマル" + ChatColor.RED + " です。");
                }
                else
                {
                    if (Mode.easy) Mode.easy = false;
                    else if (Mode.hard) Mode.hard = false;
                    Mode.normal = true;
                    clicker.closeInventory();
                    Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "ノーマル" + ChatColor.GREEN + " に変更されました。");
                }
            }
            else if (item.getType() == Material.LAVA_BUCKET)
            {
                if (Mode.hard)
                {
                    clicker.closeInventory();
                    clicker.sendMessage(Messages.PREFIX + ChatColor.RED + "難易度は既に " + ChatColor.WHITE + "ハード" + ChatColor.RED + " です。");
                }
                else
                {
                    if (Mode.easy) Mode.easy = false;
                    else if (Mode.normal) Mode.normal = false;
                    Mode.hard = true;
                    clicker.closeInventory();
                    Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GREEN + "ゲームの難易度が " + ChatColor.WHITE + "ハード" + ChatColor.GREEN + " に変更されました。");
                }
            }
        }
    }
}
