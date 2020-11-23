package plugin.midorin.info.bs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import plugin.midorin.info.bs.Teams.Entrant;

public class Scoreboards
{
    static ScoreboardManager manager = Bukkit.getScoreboardManager();
    public static Scoreboard board = manager.getMainScoreboard();
    public static Objective objective;
    static Score air0;
    static Score time_title;
    public static Score time;
    static Score air1;
    public static Score players;
    static Score air2;
    public static void setup()
    {
        manager = Bukkit.getScoreboardManager();
        board = manager.getMainScoreboard();

        objective = board.registerNewObjective("block-seek", "dummy");
        objective.setDisplayName(ChatColor.WHITE + "      BlockSeek      ");
        air0 = objective.getScore(" ");
        air0.setScore(0);
        time_title = objective.getScore(ChatColor.GOLD + "  ゲーム時間:");
        time_title.setScore(-1);
        time = objective.getScore(ChatColor.WHITE + "        00:00");
        time.setScore(-2);
        air1 = objective.getScore("  ");
        air1.setScore(-3);
        players = objective.getScore(ChatColor.RED + "  残り人数:  " + ChatColor.WHITE + "0回");
        players.setScore(-4);
        air2 = objective.getScore("   ");
        air2.setScore(-6);
        Entrant.setup();
    }
    public static void reset()
    {
        objective.getScoreboard().resetScores(air0.getEntry());
        objective.getScoreboard().resetScores(time_title.getEntry());
        objective.getScoreboard().resetScores(time.getEntry());
        objective.getScoreboard().resetScores(air1.getEntry());
        objective.getScoreboard().resetScores(players.getEntry());
        objective.getScoreboard().resetScores(air2.getEntry());
        objective.unregister();
        Entrant.team.unregister();
    }
    public static void setTime(int minutes, int second)
    {
        objective.getScoreboard().resetScores(time.getEntry());
        if (minutes < 10)
        {
            if (second < 10)
            {
                time = objective.getScore(ChatColor.WHITE + "        0" + minutes + ":0" + second);
                time.setScore(-2);
            }
            else
            {
                time = objective.getScore(ChatColor.WHITE + "        0" + minutes + ":" + second);
                time.setScore(-2);
            }
        }
    }
}
