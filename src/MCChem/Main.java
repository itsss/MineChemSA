package MCChem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin
{
    Logger log;
    public void onEnable()
    {
        log = this.getLogger();
        this.bondWater();
        log.info("bondWater Loaded and Added Recipe!");
        getLogger().info("MineChemSA Enabled");
    }
    public void onDisable()
    {
        log.info("bondWater Disabled!");
        getLogger().info("MineChemSA Disabled");
    }

    public void bondWater()
    {
        ShapedRecipe recipe = new ShapedRecipe(new ItemStack(Material.WATER_BUCKET, 1));
        recipe.shape("   ", "ABA", "   ");
        recipe.setIngredient('A', Material.SPONGE);
        recipe.setIngredient('B', Material.STONE);
        this.getServer().addRecipe(recipe);

    }
}