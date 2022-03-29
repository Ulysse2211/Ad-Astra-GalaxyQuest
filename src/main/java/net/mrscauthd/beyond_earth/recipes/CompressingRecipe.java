package net.mrscauthd.beyond_earth.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.registry.ModRecipeTypes;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class CompressingRecipe extends ModRecipe {

    public static final Identifier RECIPE_ID = new ModIdentifier("compressing");

    public CompressingRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
        super(id, input, output, cookTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.COMPRESSING_RECIPE;
    }


    public static class Serializer implements RecipeSerializer<CompressingRecipe> {

        @Override
        public CompressingRecipe read(Identifier id, JsonObject json) {
            Item item = Registry.ITEM.get(new Identifier(json.get("output").getAsJsonObject().get("item").getAsString()));
            short stackSize = json.get("output").getAsJsonObject().get("count").getAsShort();

            Ingredient input = Ingredient.fromJson(json.get("input"));
            ItemStack output = new ItemStack(item, stackSize);
            short cookTime = json.get("cookTime").getAsShort();
            return new CompressingRecipe(id, input, output, cookTime);
        }

        @Override
        public CompressingRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            short cookTime = buf.readShort();
            return new CompressingRecipe(id, input, output, cookTime);
        }

        @Override
        public void write(PacketByteBuf buf, CompressingRecipe recipe) {
            recipe.getInputIngredient().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeShort(recipe.cookTime);
        }
    }
}
