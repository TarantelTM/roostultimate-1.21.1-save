package net.tarantel.chickenroost.api.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.tarantel.chickenroost.recipes.Trainer_Recipe;

public class EmiTrainerRecipe extends BasicEmiRecipe {

    public EmiTrainerRecipe(Trainer_Recipe recipe) {
        super(EmiPlugin.TRAINER_CATEGORY, recipe.getId(), 100, 20);
        this.inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
        this.outputs.add(EmiStack.of(recipe.getResultEmi()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 50, 0);
        widgets.addSlot(inputs.get(0), 80, 0);
        widgets.addAnimatedTexture(EmiTexture.FULL_ARROW, 50, 0,
                500, true, false, false);
        widgets.addSlot(outputs.get(0), 20, 0).recipeContext(this).getRecipe();
    }
}