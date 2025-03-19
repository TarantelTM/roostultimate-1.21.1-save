package net.tarantel.chickenroost.screen;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.recipes.*;
import net.tarantel.chickenroost.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Guide_Screen extends Screen {
    private List<Button> menuButtons = new ArrayList<>(); // Liste der Menübuttons
    private String currentContent = ""; // Aktueller Inhalt der Contentbox
    private List<? extends Recipe<?>> recipes; // Liste der Rezepte (generisch)
    private int scrollOffset = 0; // Scroll-Offset
    private RecipeType<?> currentRecipeType; // Aktueller Rezepttyp

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
        // Menüeinträge auf der linken Seite erstellen
        int menuWidth = 150; // Breite der Menüleiste
        int menuMargin = 10; // Abstand vom Rand
        int buttonHeight = 20; // Höhe jedes Buttons
        int buttonSpacing = 5; // Abstand zwischen den Buttons

        String[] menuItems = {
                "Start",
                "Roost Recipes",
                "Breeder Recipes",
                "Soul Extractor",
                "Trainer",
                "Colored Chicken"
        };

        for (int i = 0; i < menuItems.length; i++) {
            int buttonY = menuMargin + i * (buttonHeight + buttonSpacing); // Y-Position für jeden Button
            int menuIndex = i; // Lokale Kopie der Schleifenvariable, effektiv final
            Button button = Button.builder(Component.literal(menuItems[i]), buttonWidget -> onMenuButtonClick(menuIndex))
                    .bounds(menuMargin, buttonY, menuWidth - 2 * menuMargin, buttonHeight)
                    .build();
            menuButtons.add(button);
            this.addRenderableWidget(button); // Button zum Screen hinzufügen
        }

        // Close-Button unten rechts
        int closeButtonWidth = 100;
        int closeButtonHeight = 20;
        int closeMargin = 10;

        int closeX = this.width - closeButtonWidth - closeMargin;
        int closeY = this.height - closeButtonHeight - closeMargin;

        this.addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
                .bounds(closeX, closeY, closeButtonWidth, closeButtonHeight)
                .build());

        // Scroll-Buttons hinzufügen
        int scrollButtonWidth = 20;
        int scrollButtonHeight = 20;
        int scrollButtonX = this.width - scrollButtonWidth - 10; // Rechts oben
        int scrollButtonY = 10;

        // Scroll-Up-Button
        this.addRenderableWidget(Button.builder(Component.literal("↑"), button -> scrollUp())
                .bounds(scrollButtonX, scrollButtonY, scrollButtonWidth, scrollButtonHeight)
                .build());

        // Scroll-Down-Button
        this.addRenderableWidget(Button.builder(Component.literal("↓"), button -> scrollDown())
                .bounds(scrollButtonX, scrollButtonY + scrollButtonHeight + 5, scrollButtonWidth, scrollButtonHeight)
                .build());

        // Standardmäßig den Start-Menüpunkt auswählen
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

//    @Override
//    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        //this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
//        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
//
//        // Contentbox auf der rechten Seite zeichnen
//        int contentBoxX = 160; // X-Position der Contentbox
//        int contentBoxY = 10; // Y-Position der Contentbox
//        int contentBoxWidth = this.width - 180; // Breite der Contentbox
//        int contentBoxHeight = this.height - 60; // Höhe der Contentbox
//
//        // Rahmen der Contentbox zeichnen
//        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000); // Schwarzer Hintergrund
//        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF); // Weißer Rahmen
//
//        // Inhalt der Contentbox zeichnen
//        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF, false); // Text in der Contentbox
//
//        // Scrollview-Inhalt rendern
//        int startY = contentBoxY + 30; // Start-Y-Position für die Rezepte
//        int slotSpacing = 30; // Abstand zwischen den Rezepten
//
//        if (currentRecipeType != null) {
//            for (int i = 0; i < recipes.size(); i++) {
//                Recipe<?> recipe = recipes.get(i);
//
//                // Slot-Hintergründe rendern
//                int slotX1 = contentBoxX + 10;
//                int slotX2 = contentBoxX + 60;
//                int slotX3 = contentBoxX + 100;
//                int outputslott = contentBoxX + 160;
//                int slotY = startY + i * slotSpacing - scrollOffset;
//
//
//                // Input 1
//                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
//                if (input1 != null) { // Überprüfen, ob input1 nicht null ist
//                    guiGraphics.renderItem(input1, slotX1, slotY);
//                }
//                ItemStack input2 = ItemStack.EMPTY;
//                // Input 2 (falls vorhanden)
//                if (recipe.getIngredients().size() > 1) {
//                    input2 = recipe.getIngredients().get(1).getItems()[0];
//                    if (input2 != null) { // Überprüfen, ob input2 nicht null ist
//                        guiGraphics.renderItem(input2, slotX2, slotY);
//                    }
//                }
//                ItemStack input3 = ItemStack.EMPTY;
//                // Input 3 (falls vorhanden)
//                if (recipe.getIngredients().size() > 2) {
//                    input3 = recipe.getIngredients().get(2).getItems()[0];
//                    if (input3 != null) { // Überprüfen, ob input3 nicht null ist
//                        guiGraphics.renderItem(input3, slotX3, slotY);
//                    }
//                }
//
//                // Pfeil zwischen Inputs und Output
//                int arrowX = slotX3 + 24; // Position des Pfeils zwischen Input 2 und Output
//                int arrowY = slotY + 2;
//                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10);
//
//                // Output
//                ItemStack output = recipe.getResultItem(null);
//                if (output != null) { // Überprüfen, ob output nicht null ist
//                    guiGraphics.renderItem(output, arrowX + 50, slotY);
//                }
//
//                // Tooltips für Items anzeigen
//                if (isHovering(slotX1, slotY, 16, 16, mouseX, mouseY) && input1 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input1, mouseX, mouseY);
//                }
//                if (isHovering(slotX2, slotY, 16, 16, mouseX, mouseY) && input2 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input2, mouseX, mouseY);
//                }
//
//                if (isHovering(slotX3, slotY, 16, 16, mouseX, mouseY) && input3 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input3, mouseX, mouseY);
//                }
//                if (isHovering(arrowX + 50, slotY, 16, 16, mouseX, mouseY) && output != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
//                }
//
//                // Slot-Hintergrund für Input 1
//                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//
//                // Slot-Hintergrund für Input 2
//                if(input2 != ItemStack.EMPTY) {
//                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }else {
//                    slotX2 = slotX1;
//                    //arrowX =- 50;
//                }
//                // Slot-Hintergrund für Input 3
//                if(input3 != ItemStack.EMPTY){
//                guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }else {
//                    slotX3 = slotX2;
//                    //arrowX =- 100;
//                }
//                // Slot-Hintergrund für Output
//                guiGraphics.blit(SLOT, arrowX + 50 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//            }
//
//        }
//
//        super.render(guiGraphics, mouseX, mouseY, partialTicks);
//    }

//    @Override
//    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
//
//        // Contentbox auf der rechten Seite zeichnen
//        int contentBoxX = 160; // X-Position der Contentbox
//        int contentBoxY = 10; // Y-Position der Contentbox
//        int contentBoxWidth = this.width - 180; // Breite der Contentbox
//        int contentBoxHeight = this.height - 60; // Höhe der Contentbox
//
//        // Rahmen der Contentbox zeichnen
//        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000); // Schwarzer Hintergrund
//        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF); // Weißer Rahmen
//
//        // Inhalt der Contentbox zeichnen
//        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF, false); // Text in der Contentbox
//
//        // Scrollview-Inhalt rendern
//        int startY = contentBoxY + 30; // Start-Y-Position für die Rezepte
//        int slotSpacing = 30; // Abstand zwischen den Rezepten
//
//        if (currentRecipeType != null) {
//            for (int i = 0; i < recipes.size(); i++) {
//                Recipe<?> recipe = recipes.get(i);
//
//                // Anzahl der Zutaten im Rezept
//                int ingredientCount = recipe.getIngredients().size();
//
//                // Slot-Hintergründe rendern
//                int slotX1 = contentBoxX + 10;
//                int slotX2 = slotX1 + (ingredientCount > 1 ? 40 : 0); // Nur verschieben, wenn es mehr als eine Zutat gibt
//                int slotX3 = slotX2 + (ingredientCount > 2 ? 40 : 0); // Nur verschieben, wenn es mehr als zwei Zutaten gibt
//                int outputSlotX = slotX3 + 40 + (ingredientCount > 2 ? 60 : 40); // Position des Output-Slots basierend auf der Anzahl der Zutaten
//                int slotY = startY + i * slotSpacing - scrollOffset;
//
//                // Leichten grauen Hintergrund hinter die Slots und den Pfeil zeichnen
//                int backgroundWidth = outputSlotX + 20 - slotX1; // Breite des Hintergrunds basierend auf der Position des Output-Slots
//                guiGraphics.fill(slotX1 - 5, slotY - 5, slotX1 + backgroundWidth + 5, slotY + 25, 0x80AAAAAA); // Grauer Hintergrund
//
//                // Input 1
//                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
//                if (input1 != null) {
//                    guiGraphics.renderItem(input1, slotX1, slotY);
//                }
//
//                // Input 2 (falls vorhanden)
//                ItemStack input2 = ItemStack.EMPTY;
//                if (ingredientCount > 1) {
//                    input2 = recipe.getIngredients().get(1).getItems()[0];
//                    if (input2 != null) {
//                        guiGraphics.renderItem(input2, slotX2, slotY);
//                    }
//                }
//
//                // Input 3 (falls vorhanden)
//                ItemStack input3 = ItemStack.EMPTY;
//                if (ingredientCount > 2) {
//                    input3 = recipe.getIngredients().get(2).getItems()[0];
//                    if (input3 != null) {
//                        guiGraphics.renderItem(input3, slotX3, slotY);
//                    }
//                }
//
//                // Pfeil zwischen Inputs und Output
//                int arrowX = slotX3 + (ingredientCount > 2 ? 40 : 20); // Position des Pfeils basierend auf der Anzahl der Zutaten
//                int arrowY = slotY + 2;
//                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10);
//
//                // Output
//                ItemStack output = recipe.getResultItem(null);
//                if (output != null) {
//                    guiGraphics.renderItem(output, outputSlotX, slotY);
//                }
//
//                // Tooltips für Items anzeigen
//                if (isHovering(slotX1, slotY, 16, 16, mouseX, mouseY) && input1 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input1, mouseX, mouseY);
//                }
//                if (isHovering(slotX2, slotY, 16, 16, mouseX, mouseY) && input2 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input2, mouseX, mouseY);
//                }
//                if (isHovering(slotX3, slotY, 16, 16, mouseX, mouseY) && input3 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input3, mouseX, mouseY);
//                }
//                if (isHovering(outputSlotX, slotY, 16, 16, mouseX, mouseY) && output != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
//                }
//
//                // Slot-Hintergrund für Input 1
//                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//
//                // Slot-Hintergrund für Input 2 (falls vorhanden)
//                if (input2 != ItemStack.EMPTY) {
//                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }
//
//                // Slot-Hintergrund für Input 3 (falls vorhanden)
//                if (input3 != ItemStack.EMPTY) {
//                    guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }
//
//                // Slot-Hintergrund für Output
//                guiGraphics.blit(SLOT, outputSlotX - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//            }
//        }
//
//        super.render(guiGraphics, mouseX, mouseY, partialTicks);
//    }

//    @Override
//    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
//
//        // Contentbox auf der rechten Seite zeichnen
//        int contentBoxX = 160; // X-Position der Contentbox
//        int contentBoxY = 10; // Y-Position der Contentbox
//        int contentBoxWidth = this.width - 180; // Breite der Contentbox
//        int contentBoxHeight = this.height - 60; // Höhe der Contentbox
//
//        // Rahmen der Contentbox zeichnen
//        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000); // Schwarzer Hintergrund
//        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF); // Weißer Rahmen
//
//        // Inhalt der Contentbox zeichnen
//        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF, false); // Text in der Contentbox
//
//        // Scrollview-Inhalt rendern
//        int startY = contentBoxY + 30; // Start-Y-Position für die Rezepte
//        int slotSpacing = 30; // Abstand zwischen den Rezepten
//
//        if (currentRecipeType != null) {
//            for (int i = 0; i < recipes.size(); i++) {
//                Recipe<?> recipe = recipes.get(i);
//
//                // Anzahl der Zutaten im Rezept
//                int ingredientCount = recipe.getIngredients().size();
//
//                // Slot-Hintergründe rendern
//                int slotX1 = contentBoxX + 10;
//                int slotX2 = slotX1 + (ingredientCount > 1 ? 40 : 0); // Nur verschieben, wenn es mehr als eine Zutat gibt
//                int slotX3 = slotX2 + (ingredientCount > 2 ? 40 : 0); // Nur verschieben, wenn es mehr als zwei Zutaten gibt
//                int outputSlotX = slotX3 + 40 + (ingredientCount > 2 ? 60 : 40); // Position des Output-Slots basierend auf der Anzahl der Zutaten
//                //int outputSlotX = slotX3 + (ingredientCount > 2 ? 60 : 40); // Position des Output-Slots basierend auf der Anzahl der Zutaten
//                int slotY = startY + i * slotSpacing - scrollOffset;
//
//                // Leichten grauen Hintergrund hinter die Slots und den Pfeil zeichnen
//                int backgroundWidth = outputSlotX + 20 - slotX1; // Breite des Hintergrunds basierend auf der Position des Output-Slots
//                guiGraphics.fill(slotX1 - 5, slotY - 5, slotX1 + backgroundWidth + 5, slotY + 25, 0x80AAAAAA); // Grauer Hintergrund
//
//                // Input 1 und Input 2 tauschen für Roost und Breeder Recipes
//                if (currentRecipeType == ModRecipes.ROOST_TYPE.get() || currentRecipeType == ModRecipes.BASIC_BREEDING_TYPE.get()) {
//                    int temp = slotX1;
//                    slotX1 = slotX2;
//                    slotX2 = temp;
//                }
//
//                // Input 1 und Output tauschen für Trainer Recipes
//                if (currentRecipeType == ModRecipes.TRAINER_TYPE.get()) {
//                    int temp = slotX1;
//                    slotX1 = outputSlotX;
//                    outputSlotX = temp;
//                }
//
//                // Input 1
//                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
//                if (input1 != null) {
//                    guiGraphics.renderItem(input1, slotX1, slotY);
//                }
//
//                // Input 2 (falls vorhanden)
//                ItemStack input2 = ItemStack.EMPTY;
//                if (ingredientCount > 1) {
//                    input2 = recipe.getIngredients().get(1).getItems()[0];
//                    if (input2 != null) {
//                        guiGraphics.renderItem(input2, slotX2, slotY);
//                    }
//                }
//
//                // Input 3 (falls vorhanden)
//                ItemStack input3 = ItemStack.EMPTY;
//                if (ingredientCount > 2) {
//                    input3 = recipe.getIngredients().get(2).getItems()[0];
//                    if (input3 != null) {
//                        guiGraphics.renderItem(input3, slotX3, slotY);
//                    }
//                }
//
//                // Pfeil zwischen Inputs und Output
//                int arrowX = slotX3 + (ingredientCount > 2 ? 40 : 20); // Position des Pfeils basierend auf der Anzahl der Zutaten
//                int arrowY = slotY + 2;
//                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10);
//
//                // Output
//                ItemStack output = recipe.getResultItem(null);
//                if (output != null) {
//                    guiGraphics.renderItem(output, outputSlotX, slotY);
//                }
//
//                // Tooltips für Items anzeigen
//                if (isHovering(slotX1, slotY, 16, 16, mouseX, mouseY) && input1 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input1, mouseX, mouseY);
//                }
//                if (isHovering(slotX2, slotY, 16, 16, mouseX, mouseY) && input2 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input2, mouseX, mouseY);
//                }
//                if (isHovering(slotX3, slotY, 16, 16, mouseX, mouseY) && input3 != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, input3, mouseX, mouseY);
//                }
//                if (isHovering(outputSlotX, slotY, 16, 16, mouseX, mouseY) && output != ItemStack.EMPTY) {
//                    guiGraphics.renderTooltip(this.font, output, mouseX, mouseY);
//                }
//
//                // Slot-Hintergrund für Input 1
//                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//
//                // Slot-Hintergrund für Input 2 (falls vorhanden)
//                if (input2 != ItemStack.EMPTY) {
//                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }
//
//                // Slot-Hintergrund für Input 3 (falls vorhanden)
//                if (input3 != ItemStack.EMPTY) {
//                    guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//                }
//
//                // Slot-Hintergrund für Output
//                guiGraphics.blit(SLOT, outputSlotX - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
//            }
//        }
//
//        super.render(guiGraphics, mouseX, mouseY, partialTicks);
//    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);

        // Contentbox auf der rechten Seite zeichnen
        int contentBoxX = 160; // X-Position der Contentbox
        int contentBoxY = 10; // Y-Position der Contentbox
        int contentBoxWidth = this.width - 180; // Breite der Contentbox
        int contentBoxHeight = this.height - 60; // Höhe der Contentbox

        // Rahmen der Contentbox zeichnen
        guiGraphics.fill(contentBoxX, contentBoxY, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight, 0xFF000000); // Schwarzer Hintergrund
        guiGraphics.renderOutline(contentBoxX, contentBoxY, contentBoxWidth, contentBoxHeight, 0xFFFFFFFF); // Weißer Rahmen

        // Inhalt der Contentbox zeichnen
        guiGraphics.drawString(this.font, currentContent, contentBoxX + 10, contentBoxY + 10, 0xFFFFFF, false); // Text in der Contentbox

        // Scrollview-Inhalt rendern
        int startY = contentBoxY + 30; // Start-Y-Position für die Rezepte
        int slotSpacing = 30; // Abstand zwischen den Rezepten

        // Enable scissor test to clip content to the content box
        guiGraphics.enableScissor(contentBoxX, contentBoxY + 25, contentBoxX + contentBoxWidth, contentBoxY + contentBoxHeight - 5);

        if (currentRecipeType != null) {
            for (int i = 0; i < recipes.size(); i++) {
                Recipe<?> recipe = recipes.get(i);

                // Anzahl der Zutaten im Rezept
                int ingredientCount = recipe.getIngredients().size();

                // Slot-Hintergründe rendern
                int slotX1 = contentBoxX + 10;
                int slotX2 = slotX1 + (ingredientCount > 1 ? 40 : 0); // Nur verschieben, wenn es mehr als eine Zutat gibt
                int slotX3 = slotX2 + (ingredientCount > 2 ? 40 : 0); // Nur verschieben, wenn es mehr als zwei Zutaten gibt
                int outputSlotX = slotX3 + 40 + (ingredientCount > 2 ? 60 : 40); // Position des Output-Slots basierend auf der Anzahl der Zutaten
                int slotY = startY + i * slotSpacing - scrollOffset;

                // Leichten grauen Hintergrund hinter die Slots und den Pfeil zeichnen
                int backgroundWidth = outputSlotX + 20 - slotX1; // Breite des Hintergrunds basierend auf der Position des Output-Slots
                guiGraphics.fill(slotX1 - 5, slotY - 5, slotX1 + backgroundWidth + 5, slotY + 25, 0x80AAAAAA); // Grauer Hintergrund

                // Input 1 und Input 2 tauschen für Roost und Breeder Recipes
                if (currentRecipeType == ModRecipes.ROOST_TYPE.get() || currentRecipeType == ModRecipes.BASIC_BREEDING_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = slotX2;
                    slotX2 = temp;
                }

                // Input 1 und Output tauschen für Trainer Recipes
                if (currentRecipeType == ModRecipes.TRAINER_TYPE.get()) {
                    int temp = slotX1;
                    slotX1 = outputSlotX;
                    outputSlotX = temp;
                }

                // Input 1
                ItemStack input1 = recipe.getIngredients().get(0).getItems()[0];
                if (input1 != null) {
                    guiGraphics.renderItem(input1, slotX1, slotY);
                }

                // Input 2 (falls vorhanden)
                ItemStack input2 = ItemStack.EMPTY;
                if (ingredientCount > 1) {
                    input2 = recipe.getIngredients().get(1).getItems()[0];
                    if (input2 != null) {
                        guiGraphics.renderItem(input2, slotX2, slotY);
                    }
                }

                // Input 3 (falls vorhanden)
                ItemStack input3 = ItemStack.EMPTY;
                if (ingredientCount > 2) {
                    input3 = recipe.getIngredients().get(2).getItems()[0];
                    if (input3 != null) {
                        guiGraphics.renderItem(input3, slotX3, slotY);
                    }
                }

                // Pfeil zwischen Inputs und Output
                int arrowX = slotX3 + (ingredientCount > 2 ? 40 : 20); // Position des Pfeils basierend auf der Anzahl der Zutaten
                int arrowY = slotY + 2;
                guiGraphics.blit(ARROW, arrowX, arrowY, 0, 0, 40, 10, 40, 10);

                // Output
                ItemStack output = recipe.getResultItem(null);
                if (output != null) {
                    guiGraphics.renderItem(output, outputSlotX, slotY);
                }

                // Tooltips für Items anzeigen
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

                // Slot-Hintergrund für Input 1
                guiGraphics.blit(SLOT, slotX1 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);

                // Slot-Hintergrund für Input 2 (falls vorhanden)
                if (input2 != ItemStack.EMPTY) {
                    guiGraphics.blit(SLOT, slotX2 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                // Slot-Hintergrund für Input 3 (falls vorhanden)
                if (input3 != ItemStack.EMPTY) {
                    guiGraphics.blit(SLOT, slotX3 - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
                }

                // Slot-Hintergrund für Output
                guiGraphics.blit(SLOT, outputSlotX - 2, slotY - 2, 0, 0, 20, 20, 20, 20);
            }
        }

        // Disable scissor test after rendering the content
        guiGraphics.disableScissor();

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    // Methode, die aufgerufen wird, wenn ein Menübutton geklickt wird
    private void onMenuButtonClick(int menuIndex) {
        Level level = Minecraft.getInstance().player.level();
        scrollOffset = 0;
        if (level == null) return;

        switch (menuIndex) {
            case 0: // Start
                currentContent = "Welcome to the Chicken Roost Guide!";
                currentRecipeType = null;
                recipes = List.of();
                break;
            case 1: // Roost Recipes
                currentContent = "Roost Recipes";
                currentRecipeType = ModRecipes.ROOST_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Roost_Recipe>) currentRecipeType)
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null) // Filtere ungültige Rezepte
                        .toList();
                break;
            case 2: // Breeder Recipes
                currentContent = "Breeder Recipes";
                currentRecipeType = ModRecipes.BASIC_BREEDING_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Breeder_Recipe>) currentRecipeType) // Typumwandlung
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null) // Filtere ungültige Rezepte
                        .toList();
                break;
            case 3: // Soul Extractor
                currentContent = "Soul Extractor Recipes";
                currentRecipeType = ModRecipes.SOUL_EXTRACTION_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Soul_Extractor_Recipe>) currentRecipeType) // Typumwandlung
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null) // Filtere ungültige Rezepte
                        .toList();
                break;
            case 4: // Trainer
                currentContent = "Trainer Recipes";
                currentRecipeType = ModRecipes.TRAINER_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<Trainer_Recipe>) currentRecipeType) // Typumwandlung
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null) // Filtere ungültige Rezepte
                        .toList();
                break;
            case 5: // Colored Chicken
                currentContent = "Colored Chicken Recipes";
                currentRecipeType = ModRecipes.THROW_EGG_TYPE.get();
                recipes = level.getRecipeManager()
                        .getAllRecipesFor((RecipeType<ThrowEggRecipe>) currentRecipeType) // Typumwandlung
                        .stream()
                        .map(RecipeHolder::value)
                        .filter(recipe -> recipe.getResultItem(null) != null) // Filtere ungültige Rezepte
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
        // Nach oben scrollen
        scrollOffset = Math.max(0, scrollOffset - 30);
    }

    private void scrollDown() {
        // Nach unten scrollen
        scrollOffset += 30;
    }
}