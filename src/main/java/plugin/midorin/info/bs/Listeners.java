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
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        Material sel_block = Game.select_block.get(0);
        if (sel_block == null || block == null) return;

        if (Game.now)
            if (Game.countdown == 0)
            {
                if (block.getType().equals(sel_block))
                {
                    p.sendMessage("ダイヤモンドブロックを踏みました");
                    if (Mode.easy || Mode.normal)
                    {
                        p.sendMessage(Messages.PREFIX + ChatColor.GOLD + "あなたは次の試合への出場権利を取得しました。");
                    }
                    else if (Mode.hard)
                    {
                        // 乗っていたらポイントを加算
                    }
                }
                else
                {
                    p.setHealth(0.0d);
                    Entrant.removePlayer(p, false);
                }
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
        if (item.getType() == Game.select_block.get(0))
        {
            e.setCancelled(true);
            clicker.closeInventory();
        }
        else if (e.getInventory().getName().equalsIgnoreCase(Mode.gui.getName()))
        {
            if (item.getType() == Material.MILK_BUCKET)
            {
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
