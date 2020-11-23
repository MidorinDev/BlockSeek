package plugin.midorin.info.bs;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import plugin.midorin.info.bs.Teams.Entrant;
import plugin.midorin.info.bs.commands.Mode;
import plugin.midorin.info.bs.util.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game
{
    public static int before = 5;
    public static int countdown = 120;
    public static int m = 2;
    public static BukkitRunnable timer;
    public static boolean now = false;

    public static Material select_block = null;
    // 試合回数
    public static int times = 5;

    public static void help1(CommandSender sender)
    {
        sender.sendMessage(ChatColor.WHITE + "------------------" + ChatColor.AQUA + "BlockSeek" + ChatColor.WHITE + "-------------1/2--");
        sender.sendMessage(ChatColor.GOLD + "/bs help" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "コマンドのヘルプ表示");
        sender.sendMessage(ChatColor.GOLD + "/bs start" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ゲームを開始");
        sender.sendMessage(ChatColor.GOLD + "/bs finish" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ゲームを終了");
        sender.sendMessage(ChatColor.GOLD + "/bs setblock" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ブロックを設定");
        sender.sendMessage(ChatColor.GOLD + "/bs range <範囲>" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "マップ範囲を変更");
        sender.sendMessage(ChatColor.GOLD + "/bs spawn" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "リス地を設定");
        sender.sendMessage(ChatColor.GOLD + "/bs reset" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ゲームをリセット");
        sender.sendMessage(ChatColor.WHITE + "----------------------------------------------");
    }
    public static void help2(CommandSender sender)
    {
        sender.sendMessage(ChatColor.WHITE + "------------------" + ChatColor.AQUA + "BlockSeek" + ChatColor.WHITE + "-------------1/2--");
        sender.sendMessage(ChatColor.GOLD + "/mode" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "難易度設定画面を表示");
        sender.sendMessage(ChatColor.GOLD + "/mode easy" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "イージーに設定");
        sender.sendMessage(ChatColor.GOLD + "/mode normal" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ノーマルに設定");
        sender.sendMessage(ChatColor.GOLD + "/mode hard" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ハードに設定");
        sender.sendMessage(ChatColor.WHITE + "----------------------------------------------");
    }
    public static void start_count()
    {
        BukkitRunnable task1 = new BukkitRunnable()
        {
            public void run()
            {
                if (before == 0)
                {
                    this.cancel();
                    start();
                }
                else
                {
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        ap.sendTitle(ChatColor.GREEN + " " + before + " ", ChatColor.GRAY + "開始まで・・・", 10, 10, 10);
                        ap.getLocation().getWorld().playSound(ap.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
                before--;
            }
        };
        task1.runTaskTimer(BlockSeek.getPlugin(), 0L, 20L);
    }
    public static void start()
    {
        Scoreboards.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            Location loc = ap.getPlayer().getLocation();
            loc.getWorld().playSound(loc, Sound.ENTITY_ENDERDRAGON_AMBIENT, 50, 1);
            ap.sendTitle(ChatColor.GOLD + "ゲーム開始！", ChatColor.GRAY + "BlockSeek", 10, 10, 10);
            ap.setGameMode(GameMode.ADVENTURE);
            ap.getInventory().setItem(8, new ItemStack(select_block));
        }
        System.out.println(m);
        timer = new BukkitRunnable()
        {
            public void run()
            {
                if (countdown == 300) m=m-1;
                else if (countdown == 240) m=m-1;
                else if (countdown == 180) m=m-1;
                else if (countdown == 120) m=m-1;
                else if (countdown == 60) m=m-1;
                Scoreboards.setTime(m, countdown - m * 60);
                Scoreboards.objective.getScoreboard().resetScores(Scoreboards.players.getEntry());
                Scoreboards.players = Scoreboards.objective.getScore(ChatColor.RED + "  残り人数:  " + ChatColor.WHITE + "0回");
                Scoreboards.players.setScore(-4);

                if (countdown == 0)
                {
                    Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GRAY + "プレイヤーデータ処理中です...");
                    for (Player sp : Bukkit.getOnlinePlayers())
                    {
                        if (!BlockSeek.players.contains(sp))
                        {
                            BlockSeek.players.remove(sp);
                            Entrant.removePlayer(sp, false);
                            sp.setHealth(0.0d);
                        }
                    }
                    Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.YELLOW + "--------" + ChatColor.GOLD + "次の試合の参加者" + ChatColor.YELLOW + "--------");
                    for (int i=0; i<BlockSeek.players.size(); ++i)
                        Bukkit.broadcastMessage(BlockSeek.players.get(i).getName());
                    this.cancel();
                }
                countdown--;
            }
        };
        timer.runTaskTimer(BlockSeek.getPlugin(), 0L, 20L);
    }
    public static void reset()
    {
        if (timer != null) timer.cancel();
        now = false;
        Scoreboards.reset();
        Mode.gui.clear();
        before = 5;
        countdown = 120;
        m = 2;
        if (BlockSeek.players != null) BlockSeek.players.clear();
        times = 5;
        Mode.easy = false;
        Mode.normal = false;
        Mode.hard = false;
        if (select_block != null) select_block = null;
        Items.setup();
        Mode.gui.setItem(20, Items.milk_bucket);
        Mode.gui.setItem(22, Items.water_bucket);
        Mode.gui.setItem(24, Items.lava_bucket);
        Scoreboards.setup();
    }
    public static void finish()
    {
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            ap.sendTitle(ChatColor.GOLD + "ゲーム終了", ChatColor.GRAY + "BlockSeek", 10, 10 ,10);
            ap.getLocation().getWorld().playSound(ap.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 50, 1);
            ap.setGameMode(GameMode.SPECTATOR);
        }
        timer.cancel();
    }
}
