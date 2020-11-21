package plugin.midorin.info.bs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items
{
    public static void setup()
    {
        itemMeta1.setDisplayName(ChatColor.WHITE + "イージーモード");
        milk_bucket.setItemMeta(itemMeta1);
        itemMeta2.setDisplayName(ChatColor.DARK_AQUA + "ノーマルモード");
        water_bucket.setItemMeta(itemMeta2);
        itemMeta3.setDisplayName(ChatColor.RED + "ハードモード");
        lava_bucket.setItemMeta(itemMeta3);
    }
    public static final ItemStack milk_bucket = new ItemStack(Material.MILK_BUCKET);
    static final ItemMeta itemMeta1 = milk_bucket.getItemMeta();
    public static final ItemStack water_bucket = new ItemStack(Material.WATER_BUCKET);
    static final ItemMeta itemMeta2 = water_bucket.getItemMeta();
    public static final ItemStack lava_bucket = new ItemStack(Material.LAVA_BUCKET);
    static final ItemMeta itemMeta3 = lava_bucket.getItemMeta();
    public static void setBlock()
    {
        s_block = new ItemStack(Game.select_block.get(0));
        itemMeta4.setDisplayName(ChatColor.GOLD + "次の指定ブロックです。");
        s_block.setItemMeta(itemMeta4);
    }
    public static ItemStack s_block = new ItemStack(Game.select_block.get(0));
    static final ItemMeta itemMeta4 = s_block.getItemMeta();

}
