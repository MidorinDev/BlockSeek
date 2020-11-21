package plugin.midorin.info.bs.Teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import plugin.midorin.info.bs.Scoreboards;
import plugin.midorin.info.bs.util.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Entrant
{
    public static final String TEAM = "entrant";
    public static Team team;
    public static List<UUID> teamList = new ArrayList<UUID>();
    public static void setup()
    {
        team = Scoreboards.board.getTeam(TEAM);
        if (team == null)
        {
            team = Scoreboards.board.registerNewTeam(TEAM);
            team.setPrefix(ChatColor.RED.toString());
            team.setSuffix(ChatColor.RESET.toString());
            team.setDisplayName(ChatColor.RED + "Red");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
            team.setAllowFriendlyFire(false);
        }
    }
    public static void addPlayer(Player player, boolean message)
    {
        if (teamList.contains(player.getUniqueId())) return;
        team.addEntry(player.getName());
        teamList.add(player.getUniqueId());
        if (message)
            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GRAY + player.getName() + "がゲームに参加しました。");
    }
    public static void removePlayer(Player player, boolean message)
    {
        if (!teamList.contains(player.getUniqueId())) return;
        team.removeEntry(player.getName());
        teamList.remove(player.getUniqueId());
        if (message)
            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GRAY + player.getName() + "がゲームから離脱しました。");
    }
}
