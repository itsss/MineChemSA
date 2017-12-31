package MCChem;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionType;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener
{
    //private ShapedRecipe recipe;
    private ShapedRecipe o2, candy, metmok, metcol, hcl;
    Logger log;
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        log = this.getLogger();
        this.bondWater();
        log.info("bondWater Loaded and Added Recipe!");
        getLogger().info("MineChemSA Enabled");

        ItemStack itemstack  = new ItemStack(Material.POTION, 1);
        itemstack.setDurability((short)16428);
        hcl = new ShapedRecipe(itemstack);
        hcl.shape("   ", "CL ", "   ");
        hcl.setIngredient('L', Material.IRON_BLOCK);
        hcl.setIngredient('C', new Wool(DyeColor.ORANGE));
        this.getServer().addRecipe(hcl);

        candy = new ShapedRecipe(new ItemStack(Material.SUGAR, 1));
        candy.shape("   ", "CHO", "   ");
        candy.setIngredient('C', new Wool(DyeColor.PINK));
        candy.setIngredient('H', new Wool(DyeColor.ORANGE));
        candy.setIngredient('O', new Wool(DyeColor.SILVER));
        this.getServer().addRecipe(candy);

        //Charcoal ItemStack = new ItemStack(Material.COAL, 1, (short) 1);
        metmok = new ShapedRecipe(new ItemStack(Material.COAL, 1));
        metmok.shape("   ", "CH ", "   ");
        metmok.setIngredient('C', new Wool(DyeColor.PINK));
        metmok.setIngredient('H', new Wool(DyeColor.ORANGE));
        this.getServer().addRecipe(metmok);
    }

    public void onDisable()
    {
        log.info("bondWater Disabled!");
        getLogger().info("MineChemSA Disabled");
    }

    public void bondWater()
    {
        ShapedRecipe h2o = new ShapedRecipe(new ItemStack(Material.WATER_BUCKET, 1));
        h2o.shape("   ", "HO ", " H "); // H2O
        h2o.setIngredient('H', new Wool(DyeColor.ORANGE));
        h2o.setIngredient('O', new Wool(DyeColor.SILVER));

        ShapedRecipe nh3 = new ShapedRecipe(new ItemStack(Material.QUARTZ_BLOCK, 1));
        nh3.shape(" H ", " N ", "H H");
        nh3.setIngredient('H', new Wool(DyeColor.ORANGE));
        nh3.setIngredient('N', new Wool(DyeColor.GRAY));

        ShapedRecipe co2 = new ShapedRecipe(new ItemStack(Material.SPONGE, 1));
        co2.shape("   ", "OCO", "   ");
        co2.setIngredient('C', new Wool(DyeColor.PINK));
        co2.setIngredient('O', new Wool(DyeColor.SILVER));

        this.getServer().addRecipe(h2o);
        this.getServer().addRecipe(nh3);
        this.getServer().addRecipe(co2);
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event)
    {
        PotionMeta meta = (PotionMeta) event.getRecipe().getResult().getItemMeta();
        //log.info("meta" + meta.getBasePotionData().getType());
        if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_DAMAGE))
        {
            log.info("Instant Damage Potion Detected");
        }
        if(event.getRecipe().getResult().getType().equals(Material.SUGAR)) //사탕수수 조합 제작
        { //PINK, ORANGE, SILVER
            log.info("candy getrecipe");
            CraftingInventory inventory = event.getInventory();
            boolean found1=false, found2=false, found3=false;
            for(ItemStack item : inventory.getMatrix())
            {
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 12)
                {
                    found1 = true;
                }
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 22)
                {
                    found2 = true;
                }
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 11)
                {
                    found3 = true;
                }
            }
            if(!found1 || !found2 || !found3) inventory.setResult(null);
        }

        //메테인 목탄
        if(event.getRecipe().getResult().getType().equals(Material.COAL)) //metmok
        {
            if (event.getRecipe().getResult().getDurability() == 1)
            {
                CraftingInventory inventory = event.getInventory();
                boolean found1 = false, found2 = false;
                for (ItemStack item : inventory.getMatrix())
                {
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 2)
                    {
                        found1 = true;
                    }
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 6)
                    {
                        found2 = true;
                    }
                }
                if (!found1 || !found2) inventory.setResult(null);
            }
            else
            {
                log.info("*_*");
                CraftingInventory inventory = event.getInventory();
                boolean found1 = false, found2 = false;
                for (ItemStack item : inventory.getMatrix())
                {
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 6)
                    {
                        log.info("1");
                        found1 = true;
                    }
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 4)
                    {
                        log.info("2");
                        found2 = true;
                    }
                }
                if (!found1 || !found2) inventory.setResult(null);
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event)
    {
        log.info("onCraftItem Enabled");
        //log.info("test[onCraft] : " + );

        if (event.getRecipe().getResult().getType().equals(Material.SUGAR)) //사탕수수 조합 제작
        { //PINK, ORANGE, SILVER
            log.info("candy received");
            CraftingInventory inventory = event.getInventory();
            boolean found1 = false, found2 = false, found3 = false;
            ItemStack candy = null;
            ItemStack f1 = null, f2 = null;
            for (ItemStack item : inventory.getMatrix())
            {
                if (item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 12)
                {
                    found1 = true;
                    f1 = item;
                }
                if (item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 22)
                {
                    found2 = true;
                    candy = item;
                }
                if (item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 11)
                {
                    found3 = true;
                    f2 = item;
                }
            }
            if (!found1 || !found2 || !found3) inventory.setResult(null);
            else
            {
                candy.setAmount(candy.getAmount() - 23);
                f1.setAmount(f1.getAmount() - 13);
                f2.setAmount(f2.getAmount() - 12);
            }
        }

        //메테인 목탄
        if (event.getRecipe().getResult().getType().equals(Material.COAL)) //metmok
        {
            if (event.getRecipe().getResult().getDurability() == 1)
            {
                CraftingInventory inventory = event.getInventory();
                boolean found1 = false, found2 = false;
                ItemStack f1 = null, f2 = null;
                for (ItemStack item : inventory.getMatrix()) {
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 2)
                    {
                        f1 = item;
                        found1 = true;
                    }
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 6)
                    {
                        f2 = item;
                        found2 = true;
                    }
                }
                if (!found1 || !found2) inventory.setResult(null);
                else
                {
                    f1.setAmount(f1.getAmount() - 3);
                    f2.setAmount(f2.getAmount() - 7);
                }
            }

            else
            {
                log.info("*_*");
                CraftingInventory inventory = event.getInventory();
                boolean found1 = false, found2 = false;
                ItemStack f1 = null, f2 = null;
                for (ItemStack item : inventory.getMatrix())
                {
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 6)
                    {
                        log.info("1");
                        f1 = item;
                        found1 = true;
                    }
                    if (item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 4)
                    {
                        log.info("2");
                        f2 = item;
                        found2 = true;
                    }
                }
                if (!found1 || !found2) inventory.setResult(null);
                else
                {
                    f1.setAmount(f1.getAmount() - 2);
                    f2.setAmount(f2.getAmount() - 5);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (e.getBlockPlaced().getType().equals(Material.SPONGE))  // CO2 Effect
        {
            Player player = e.getPlayer();
            World world = player.getWorld();
            e.setCancelled(true);
            double x1 = e.getBlockPlaced().getLocation().getBlockX();
            double y1 = e.getBlockPlaced().getLocation().getBlockY();
            double z1 = e.getBlockPlaced().getLocation().getBlockZ();

            Block b1 = new Location(world, x1 + 1, y1, z1).getBlock();
            Block b2 = new Location(world, x1 - 1, y1, z1).getBlock();
            Block b3 = new Location(world, x1, y1, z1 + 1).getBlock();
            Block b4 = new Location(world, x1, y1, z1 - 1).getBlock();
            Block b5 = new Location(world, x1, y1, z1).getBlock();

            if (b1.getType().equals(Material.FIRE) || b2.getType().equals(Material.FIRE) || b3.getType().equals(Material.FIRE) || b4.getType().equals(Material.FIRE) || b5.getType().equals(Material.FIRE))
            {
                b1.setType(Material.AIR);
                b2.setType(Material.AIR);
                b3.setType(Material.AIR);
                b4.setType(Material.AIR);
                b5.setType(Material.AIR);
            }
        }

        else if (e.getBlockPlaced().getType().equals(Material.QUARTZ_BLOCK)) // NH3 Effect
        {
            log.info("DETECTED");
            Player player = e.getPlayer();
            World world = player.getWorld();
            e.setCancelled(true);
            int x1 = e.getBlockPlaced().getLocation().getBlockX();
            int y1 = e.getBlockPlaced().getLocation().getBlockY();
            int z1 = e.getBlockPlaced().getLocation().getBlockZ();

            //Block b = e.getBlock();
            Block b = new Location(world, x1, y1-1, z1).getBlock();
            System.out.println(b);
            if (b.getType().equals(Material.CROPS))
            {
                log.info("SEEDS DETECTED");
                b.setData(CropState.RIPE.getData());
            }

        }
    }
}
