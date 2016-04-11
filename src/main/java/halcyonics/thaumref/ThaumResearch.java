package halcyonics.thaumref;

import halcyonics.handler.BlocksHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

/**
 * Created by Niel on 11/4/2015.
 */
public class ThaumResearch {

    public static void init() {

        ResearchItem item = new ResearchItem("AURAIC_COLLIDER", "HALCYONICS", new AspectList().add(Aspect.AURA, 1).add(Aspect.ENERGY, 1).add(Aspect.MECHANISM, 1).add(Aspect.TOOL, 1), 0, 0, 3, new ResourceLocation("halcyonics", "textures/tc/halcyonics.png"));
        item.setParents("INFUSION");

        ResearchCategories.registerCategory("HALCYONICS", null, new ResourceLocation("halcyonics", "textures/tc/halcyonics.png"), new ResourceLocation("thaumcraft", "textures/misc/particlefield.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_4.jpg"));
        ResearchPage page = new ResearchPage(new ShapedArcaneRecipe("AURAIC_COLLIDER", new ItemStack(BlocksHandler.colliderBlock, 8), new AspectList().add(Aspect.ORDER, 5), "ABA", "B B", "ABA", 'A',
                new ItemStack(BlocksTC.stone, 1, 1), 'B', new ItemStack(ItemsTC.shard, 1, 5)));


        page.type = ResearchPage.PageType.TEXT;
        page.text = "HELLO";
        page.research = "AURAIC_COLLIDER";
        page.image = new ResourceLocation("halcyonics", "textures/tc/halcyonics.png");
        item.setPages(page);

        ResearchCategories.addResearch(item);
    }
}
