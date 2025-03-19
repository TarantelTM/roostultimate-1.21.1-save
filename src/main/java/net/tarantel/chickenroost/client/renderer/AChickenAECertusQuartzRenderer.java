
package net.tarantel.chickenroost.client.renderer;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.ChickenRoostMod;

import net.tarantel.chickenroost.entity.mods.aetwo.AChickenAECertusQuartzEntity;
import net.tarantel.chickenroost.client.model.Modelchicken;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AChickenAECertusQuartzRenderer extends MobRenderer<AChickenAECertusQuartzEntity, Modelchicken<AChickenAECertusQuartzEntity>> {
	public AChickenAECertusQuartzRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelchicken(context.bakeLayer(Modelchicken.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AChickenAECertusQuartzEntity entity) {
		return ChickenRoostMod.ownresource("textures/entities/quartzchicken.png");
	}
}
