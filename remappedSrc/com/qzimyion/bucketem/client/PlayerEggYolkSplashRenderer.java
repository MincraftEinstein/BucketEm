package com.qzimyion.bucketem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerEggYolkSplashRenderer extends RenderLayer<Player, PlayerModel<Player>> {

    public PlayerEggYolkSplashRenderer(RenderLayerParent<Player, PlayerModel<Player>> context) {
        super(context);
    }

    public static final HashMap<EggState, ResourceLocation> TEXTURE = new HashMap<>();

    static {
        TEXTURE.put(EggState.DEFAULT, new ResourceLocation("textures/entity/player/eggsplash/default_egg.png"));
        TEXTURE.put(EggState.ALT, new ResourceLocation("textures/entity/player/eggsplash/alt_egg.png"));
        TEXTURE.put(EggState.TWO_YOLKS, new ResourceLocation("textures/entity/player/eggsplash/two_yolks.png"));
        TEXTURE.put(EggState.THREE_YOLKS, new ResourceLocation("textures/entity/player/eggsplash/three_yolks.png"));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, Player entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isInvisible()){
            if (((IEgg) entity).getEgged()){
                renderColoredCutoutModel(this.getParentModel(), TEXTURE.get(((IEgg)entity).getTexture()), matrices, vertexConsumers, light, entity, -1, -1, -1);
            }
        }
    }
}
