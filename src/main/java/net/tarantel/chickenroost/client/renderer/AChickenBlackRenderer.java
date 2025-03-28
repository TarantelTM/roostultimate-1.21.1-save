
package net.tarantel.chickenroost.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.client.model.Modelchicken;
import net.tarantel.chickenroost.entity.chickens.AChickenBlackEntity;
import net.tarantel.chickenroost.entity.chickens.AChickenWaterEntity;

public class AChickenBlackRenderer extends MobRenderer<AChickenBlackEntity, Modelchicken<AChickenBlackEntity>> {
    public AChickenBlackRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AChickenBlackEntity entity) {
        return ChickenRoostMod.ownresource("textures/blackchicken.png");
    }
}
