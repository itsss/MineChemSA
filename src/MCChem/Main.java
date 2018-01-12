package MCChem;

import net.minecraft.server.v1_10_R1.BlockGlass;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener
{
    //private ShapedRecipe recipe;
    private ShapedRecipe candy, metmok, luck, hcl, egg, reg, o2, glow;
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

        candy = new ShapedRecipe(new ItemStack(Material.SUGAR, 20));
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

        Potion potionluck = new Potion(PotionType.LUCK);
        potionluck.setSplash(true);
        ItemStack itemstack2 = potionluck.toItemStack(1);
        luck = new ShapedRecipe(itemstack2);
        luck.shape("   ", "HCO", "   ");
        luck.setIngredient('H', new Wool(DyeColor.ORANGE));
        luck.setIngredient('C', new Wool(DyeColor.PINK));
        luck.setIngredient('O', new Wool(DyeColor.SILVER));
        this.getServer().addRecipe(luck);

        Potion potionreg = new Potion(PotionType.INSTANT_HEAL);
        potionreg.setSplash(true);
        ItemStack itemstack3 = potionreg.toItemStack(1);
        reg = new ShapedRecipe(itemstack3);
        reg.shape("   ", " HO", "   ");
        reg.setIngredient('H', new Wool(DyeColor.ORANGE));
        reg.setIngredient('O', new Wool(DyeColor.SILVER));
        this.getServer().addRecipe(reg);

        Potion potionbreath = new Potion(PotionType.WATER_BREATHING);
        potionbreath.setSplash(true);
        ItemStack itemstack4 = potionbreath.toItemStack(1);
        o2 = new ShapedRecipe(itemstack4);
        o2.shape("   ", " O ", "   ");
        o2.setIngredient('O', new Wool(DyeColor.SILVER));
        this.getServer().addRecipe(o2);

        egg = new ShapedRecipe(new ItemStack(Material.EGG, 1));
        egg.shape("   ", "ACO", "   ");
        ItemStack glass = new ItemStack(Material.STAINED_GLASS, 1, (short)3);
        egg.setIngredient('A', glass.getData());
        egg.setIngredient('C', new Wool(DyeColor.PINK));
        egg.setIngredient('O', new Wool(DyeColor.SILVER));
        this.getServer().addRecipe(egg);

        glow = new ShapedRecipe(new ItemStack(Material.GLOWSTONE, 1));
        glow.shape("GGG", "GNG", "GGG");
        glow.setIngredient('G', Material.GLASS);
        glow.setIngredient('N', new Wool(DyeColor.PURPLE));
        this.getServer().addRecipe(glow);
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
        if(event.getRecipe().getResult().getType().equals(Material.SPLASH_POTION))
        {
            PotionMeta meta = (PotionMeta) event.getRecipe().getResult().getItemMeta();
            /*if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL))
            {
                log.info("HEAL POTION DETECTED");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f1 = true;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f2 = true;
                    } //H2O2 => 투척치료 포션
                }
                if(!f1 || !f2) inventory.setResult(null);
            }*/

            if(meta.getBasePotionData().getType().equals(PotionType.LUCK))
            {
                log.info("luck detected");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false, f3 = false;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 1)
                    {
                        f1 = true;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f2 = true;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 3)
                    {
                        f3 = true;
                    } //H2CO3 => 행운 포션
                }
                if(!f1 || !f2 || !f3) inventory.setResult(null);
            }
            if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL))
            {
                log.info("regen detected");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f1 = true;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f2 = true;
                    }
                }
                if(!f1 || !f2) inventory.setResult(null);
            }
            if(meta.getBasePotionData().getType().equals(PotionType.WATER_BREATHING))
            {
                log.info("waterbreath detected");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f1 = true;
                    }
                }
                if(!f1) inventory.setResult(null);
            }

        }
        /*PotionMeta meta = (PotionMeta) event.getRecipe().getResult().getItemMeta();
        log.info("meta" + meta.getBasePotionData().getType());
        if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_DAMAGE))
        {
            log.info("Instant Damage Potion Detected");
        }*/
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
        if(event.getRecipe().getResult().getType().equals(Material.EGG))
        {
            log.info("egg getrecipe");
            CraftingInventory inventory = event.getInventory();
            boolean found1 = false, found2 = false, found3 = false;
            for(ItemStack item : inventory.getMatrix())
            {
                ItemStack glass = new ItemStack(Material.STAINED_GLASS, 1, (short)3);
                //egg.setIngredient('A', glass.getData());
                if(item.getType().equals(Material.STAINED_GLASS) && item.getDurability() == 3)
                {
                    log.info("gla1");
                    found1 = true;
                }
                //C = 6, O = 8
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 6)
                {
                    log.info("gla2");
                    found2 = true;
                }
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 3)
                {
                    log.info("gla3");
                    found3 = true;
                }
            }
            if(!found1 || !found2 || !found3) inventory.setResult(null);
        }
        //CaCO3 달걀
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event)
    {
        log.info("onCraftItem Enabled");
        //log.info("test[onCraft] : " + );
        if(event.getRecipe().getResult().getType().equals(Material.SPLASH_POTION))
        {
            PotionMeta meta = (PotionMeta) event.getRecipe().getResult().getItemMeta();
            /*if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL))
            {
                log.info("HEAL POTION DETECTED");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false;
                ItemStack found1 = null, found2 = null;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f1 = true;
                        found1 = item;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f2 = true;
                        found2 = item;
                    } //H2O2 => 투척치료 포션
                }
                if(!f1 || !f2) inventory.setResult(null);
                else
                {
                    found1.setAmount(found1.getAmount()-3);
                    found2.setAmount(found1.getAmount()-3);
                }
            }*/
            if(meta.getBasePotionData().getType().equals(PotionType.LUCK))
            {
                log.info("luck detected");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false, f3 = false;
                ItemStack found1 = null, found2 = null, found3 = null;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 6 && item.getAmount() >= 1)
                    {
                        f1 = true;
                        found1 = item;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f2 = true;
                        found2 = item;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 3)
                    {
                        f3 = true;
                        found3 = item;
                    } //H2CO3 => 행운 포션
                }
                if(!f1 || !f2 || !f3) inventory.setResult(null);
                else
                {
                    found1.setAmount(found1.getAmount()-2);
                    found2.setAmount(found2.getAmount()-3);
                    found3.setAmount(found3.getAmount()-4);
                }
            }

            if(meta.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL))
            {
                log.info("regen detected2");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false, f2 = false;
                ItemStack found1 = null, found2 = null;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 1 && item.getAmount() >= 2)
                    {
                        f1 = true;
                        found1 = item;
                    }
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f2 = true;
                        found2 = item;
                    }
                }
                if(!f1 || !f2) inventory.setResult(null);
                else
                {
                    found1.setAmount(found1.getAmount()-3);
                    found2.setAmount(found2.getAmount()-3);
                }
            }
            if(meta.getBasePotionData().getType().equals(PotionType.WATER_BREATHING))
            {
                log.info("waterbreath detected");
                CraftingInventory inventory = event.getInventory();
                boolean f1 = false;
                ItemStack water = null;
                for(ItemStack item : inventory.getMatrix())
                {
                    if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 2)
                    {
                        f1 = true;
                        water = item;
                    }
                }
                if(!f1) inventory.setResult(null);
                else
                {
                    water.setAmount(water.getAmount()-3);
                }
            }
        }

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
        if(event.getRecipe().getResult().getType().equals(Material.EGG))
        {
            log.info("egg getrecipe");
            CraftingInventory inventory = event.getInventory();
            boolean found1 = false, found2 = false, found3 = false;
            ItemStack f1 = null, f2 = null, f3 = null;
            for(ItemStack item : inventory.getMatrix())
            {
                //egg.setIngredient('A', glass.getData());
                if(item.getType().equals(Material.STAINED_GLASS) && item.getDurability() == 3)
                {
                    log.info("gla1");
                    f1 = item;
                    found1 = true;
                }
                //C = 6, O = 8
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 6)
                {
                    log.info("gla2");
                    f2 = item;
                    found2 = true;
                }
                if(item.getType().equals(Material.WOOL) && item.getDurability() == 8 && item.getAmount() >= 3)
                {
                    log.info("gla3");
                    f3 = item;
                    found3 = true;
                }
            }
            if(!found1 || !found2 || !found3) inventory.setResult(null);
            else
            {
                f1.setAmount(f1.getAmount()-1);
                f2.setAmount(f2.getAmount()-1);
                f3.setAmount(f3.getAmount()-4);
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

        else if (e.getBlockPlaced().getType().equals(Material.FIRE)) //H Boom
        {
            log.info("fire install");
            Player player = e.getPlayer();
            World world = player.getWorld();
            double x1 = e.getBlockPlaced().getLocation().getBlockX();
            double y1 = e.getBlockPlaced().getLocation().getBlockY();
            double z1 = e.getBlockPlaced().getLocation().getBlockZ();

            Block b1 = new Location(world, x1, y1-1, z1).getBlock();
            if(b1.getType() == Material.WOOL)
            {
                Wool wool = new Wool(b1.getType(), b1.getData());
                DyeColor color = wool.getColor();
                if(color.equals(DyeColor.ORANGE))
                {
                    log.info("fire BOOM!!!");
                    player.getWorld().createExplosion(b1.getLocation(), 6F); //규모 6F (크리퍼 4F)
                    player.getWorld().playEffect(b1.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                }
            }
        }
    }
}
