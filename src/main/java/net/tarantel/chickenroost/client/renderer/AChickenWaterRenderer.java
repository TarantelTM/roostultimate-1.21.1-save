
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.vanilla.AChickenAcaciaWoodEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tarantel.chickenroost.entity.chickens.AChickenWaterEntity;

public class AChickenWaterRenderer extends MobRenderer<AChickenWaterEntity, Modelchicken<AChickenWaterEntity>> {
    public AChickenWaterRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AChickenWaterEntity entity) {
        return ChickenRoostMod.ownresource("textures/waterchicken.png");
    }
}
