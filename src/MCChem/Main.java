package MCChem;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;


import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener
{
    private ShapedRecipe recipe;
    Logger log;
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        log = this.getLogger();
        this.bondWater();
        log.info("bondWater Loaded and Added Recipe!");
        getLogger().info("MineChemSA Enabled");

        recipe = new ShapedRecipe(new ItemStack(Material.WOOD, 1));
        recipe.shape("   ", "BA ", "   ");
        recipe.setIngredient('A', Material.STONE);
        recipe.setIngredient('B', Material.SPONGE);
        this.getServer().addRecipe(recipe);
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
        co2.shape("   ", "COC", "   ");
        co2.setIngredient('C', new Wool(DyeColor.PINK));
        co2.setIngredient('O', new Wool(DyeColor.SILVER));

        /*ShapedRecipe hcl = new ShapedRecipe(new ItemStack(Material.STONE, 1));
        //ShapedRecipe hcl = new ShapedRecipe(new ItemStack(Material.POTION, 1, (short) 16428));
        hcl.shape("   ", "HL ", "   ");
        co2.setIngredient('L', Material.IRON_BLOCK);
        co2.setIngredient('H', new Wool(DyeColor.ORANGE));*/

        this.getServer().addRecipe(h2o);
        this.getServer().addRecipe(nh3);
        this.getServer().addRecipe(co2);
        //this.getServer().addRecipe(hcl);
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event)
    {
        if (event.getRecipe().getResult().getType().equals(Material.WOOD))
        {
            CraftingInventory inventory = event.getInventory();
            boolean found1 = false, found2 = false;
            for (ItemStack item : inventory.getMatrix())
            {
                if (item.getType().equals(Material.SPONGE) && item.getAmount() >= 2)
                {
                    found1 = true;
                }
                else if (item.getType().equals(Material.STONE))
                {
                    found2 = true;
                }
            }
            if (!found1 || !found2) inventory.setResult(null);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event)
    {
        if (event.getRecipe().getResult().getType().equals(Material.WOOD))
        {
            CraftingInventory inventory = event.getInventory();
            boolean found1 = false, found2 = false;
            ItemStack sponge = null;
            for (ItemStack item : inventory.getMatrix())
            {
                if (item.getType().equals(Material.SPONGE) && item.getAmount() >= 2)
                {
                    found1 = true;
                    sponge = item;
                }
                else if (item.getType().equals(Material.STONE))
                {
                    found2 = true;
                }
            }
            if (!found1 || !found2) inventory.setResult(null);
            else
            {
                sponge.setAmount(sponge.getAmount() - event.getRecipe().getResult().getAmount());
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

            if (b1.getType().equals(Material.FIRE) || b2.getType().equals(Material.FIRE) || b3.getType().equals(Material.FIRE) || b4.getType().equals(Material.FIRE) || b5.getType().equals(Material.FIRE)) {
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
