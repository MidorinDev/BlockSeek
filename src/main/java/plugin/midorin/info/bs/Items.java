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
    public static ItemStack milk_bucket = new ItemStack(Material.MILK_BUCKET);
    public static ItemMeta itemMeta1 = milk_bucket.getItemMeta();
    public static ItemStack water_bucket = new ItemStack(Material.WATER_BUCKET);
    public static ItemMeta itemMeta2 = water_bucket.getItemMeta();
    public static ItemStack lava_bucket = new ItemStack(Material.LAVA_BUCKET);
    public static ItemMeta itemMeta3 = lava_bucket.getItemMeta();
    private ItemStack s_block;
    private ItemMeta itemMeta4;


    public Items(Material block)
    {
        s_block = new ItemStack(block);
        itemMeta4  = s_block.getItemMeta();
        itemMeta4.setDisplayName(ChatColor.GOLD + "次の指定ブロックです。");
        s_block.setItemMeta(itemMeta4);
    }
}
