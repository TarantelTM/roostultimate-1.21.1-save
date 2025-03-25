package net.tarantel.chickenroost.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.*;

import java.util.ArrayList;
import java.util.List;

public class Guide_Screen extends Screen {
    private List<Button> menuButtons = new ArrayList<>();
    private String currentContent = "";
    private List<? extends Recipe<?>> recipes;
    private int scrollOffset = 0;
    private RecipeType<?> currentRecipeType;

    public Guide_Screen() {
        super(Component.literal(""));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public int customindex = 99;

    @Override
    protected void init() {
        int menuWidth = 150;
        int menuMargin = 10;
        int buttonHeight = 20;
        int buttonSpacing = 5;

        String[] menuItems = {
                "Start",
                "Roost Recipes",
                "Breeder Recipes",
                "Soul Extractor",
                "Trainer",
                "Colored Chicken"
        };

        for (int i = 0; i < menuItems.length; i++) {
            int buttonY = menuMargin + i * (buttonHeight + buttonSpacing);
            int menuIndex = i;
            Button button = Button.builder(Component.literal(menuItems[i]), buttonWidget -> onMenuButtonClick(menuIndex))
                    .bounds(menuMargin, buttonY, menuWidth - 2 * menuMargin, buttonHeight)
                    .build();
            menuButtons.add(button);
            this.addRenderableWidget(button);
        }
        int closeButtonWidth = 100;
        int closeButtonHeight = 20;
        int closeMargin = 10;

        int closeX = this.width - closeButtonWidth - closeMargin;
        int closeY = this.height - closeButtonHeight - closeMargin;

        this.addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
                .bounds(closeX, closeY, closeButtonWidth, closeButtonHeight)
                .build());

        int scrollButtonWidth = 20;
        int scrollButtonHeight = 20;
        int scrollButtonX = this.width - scrollButtonWidth - 10;
        int scrollButtonY = 10;

        this.addRenderableWidget(Button.builder(Component.literal("↑"), button -> scrollUp())
                .bounds(scrollButtonX, scrollButtonY, scrollButtonWidth, scrollButtonHeight)
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("↓"), button -> scrollDown())
                .bounds(scrollButtonX, scrollButtonY + scrollButtonHeight + 5, scrollButtonWidth, scrollButtonHeight)
                .build());

        onMenuButtonClick(0);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.level == null) {
            this.renderPanorama(guiGraphics, partialTick);
        }
    }

    private static final ResourceLocation SLOT = ChickenRoostMod.ownresource("textures/screens/slot.png");
    private static final ResourceLocation ARROW = ChickenRoostMod.ownresource("textures/screens/arrow.png");

    private boolean isHovering(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);

        int contentBoxX = 160;
        int contentBoxY = 10;
        int contentBoxWidth = this.width - 180;
        int contentBoxHeight = this.height - 60;

        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000);
        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF);

        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF, false);

        int startY = contentBoxY + 30;
        int slotSpacing = 30;

        guiGraphics.enableScissor(contentBoxX, contentBoxY + 25, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight - 5);

        if (currentRecipeType != null) {
            for (int i = 0; i < recipes.size(); i++) {
                Recipe<?> recipe = recipes.get(i);
                int ingredientCount = recipe.getIngredients().size();
                int slotX1 = contentBoxX + 10;
                int slotX2 = slotX1 + (ingredientCount > 1 ? 40 : 0);
                int slotX3 = slotX2 + (ingredientCount > 2 ? 40 : 0);
                int outputSlotX = slotX3 + 40 + (ingredientCount > 2 ? 60 : 40);
                int slotY = startY + i * slotSpacing - scrollOffset;

                int backgroundWidth = outputSlotX + 20 - slotX1;
                guiGraphics.fill(slotX1 - 5, slotY - 5, slotX1 + backgroundWidth + 5, slotY + 25, 0x80AAAAAA);

                if (currentRecipeType == ModRecipes.ROOST_TYPE.get() || currentRecipeType == ModRecipes.BASIC_BREEDING_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = slotX2;
                    slotX2 = temp;
                }

                if (currentRecipeType == ModRecipes.TRAINER_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = outputSlotX;
                    outputSlotX = temp;
                }

                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
                if (input1 != null) {
                    guiGraphics.renderItem(input1, slotX1, slotY);
                }

                ItemStack input2 = ItemStack.EMPTY;
                if (ingredientCount > 1) {
                    input2 = recipe.getIngredients().get(1).getItems()[0];
                    if (input2 != null) {
                        guiGraphics.renderItem(input2, slotX2, slotY);
                    }
                }

                ItemStack input3 = ItemStack.EMPTY;
                if (ingredientCount > 2) {
                    input3 = recipe.getIngredients().get(2).getItems()[0];
                    if (input3 != null) {
                        guiGraphics.renderItem(input3, slotX3, slotY);
                    }
                }

                int arrowX = slotX3 + (ingredientCount > 2 ? 40 : 20);
                int arrowY = slotY + 2;
                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10);

                ItemStack output = recipe.getResultItem(null);
                if (output != null) {
                    guiGraphics.renderItem(output, outputSlotX, slotY);
                }

                if (isHovering(slotX1, slotY, 16, 16, mouseX, mouseY) && input1 != ItemStack.EMPTY) {
                    guiGraphics.renderTooltip(this.font, input1, mouseX, mouseY);
                }
                if (isHovering(slotX2, slotY, 16, 16, mouseX, mouseY) && input2 != ItemStack.EMPTY) {
                    guiGraphics.renderTooltip(this.font, input2, mouseX, mouseY);
                }
                if (isHovering(slotX3, slotY, 16, 16, mouseX, mouseY) && input3 != ItemStack.EMPTY) {
                    guiGraphics.renderTooltip(this.font, input3, mouseX, mouseY);
                }
                if (isHovering(outputSlotX, slotY, 16, 16, mouseX, mouseY) && output != ItemStack.EMPTY) {
                    guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
                }

                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);

                if (input2 != ItemStack.EMPTY) {
                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                if (input3 != ItemStack.EMPTY) {
                    guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                guiGraphics.blit(SLOT, outputSlotX - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
            }
        }

        guiGraphics.disableScissor();

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    private void onMenuButtonClick(int menuIndex) {
        Level level = Minecraft.getInstance().player.level();
        scrollOffset = 0;
        if (level == null) return;

        switch (menuIndex) {
            case 0:
                currentContent = "Welcome to the Chicken Roost Guide!";
                currentRecipeType = null;
                recipes = List.of();
                break;
            case 1:
                currentContent = "Roost Recipes";
                currentRecipeType = ModRecipes.ROOST_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Roost_Recipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null)
                        .toList();
                break;
            case 2:
                currentContent = "Breeder Recipes";
                currentRecipeType = ModRecipes.BASIC_BREEDING_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Breeder_Recipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null)
                        .toList();
                break;
            case 3:
                currentContent = "Soul Extractor Recipes";
                currentRecipeType = ModRecipes.SOUL_EXTRACTION_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Soul_Extractor_Recipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null)
                        .toList();
                break;
            case 4:
                currentContent = "Trainer Recipes";
                currentRecipeType = ModRecipes.TRAINER_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Trainer_Recipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null)
                        .toList();
                break;
            case 5:
                currentContent = "Colored Chicken Recipes";
                currentRecipeType = ModRecipes.THROW_EGG_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<ThrowEggRecipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null)
                        .toList();
                break;
            default:
                currentContent = "Invalid Menu";
                currentRecipeType = null;
                recipes = List.of();
                break;
        }
    }

    private void scrollUp() {
        scrollOffset = Math.max(0, scrollOffset - 30);
    }

    private void scrollDown() {
        scrollOffset += 30;
    }
}