package com.qzimyion.bucketem.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class PlayerEggYolkSplashRenderer extends FeatureRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {

    public PlayerEggYolkSplashRenderer(FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> context) {
        super(context);
    }

    public static final HashMap<EggState, Identifier> TEXTURE = new HashMap<>();

    static {
        TEXTURE.put(EggState.DEFAULT, new Identifier("textures/entity/player/eggsplash/default_egg.png"));
        TEXTURE.put(EggState.ALT, new Identifier("textures/entity/player/eggsplash/alt_egg.png"));
        TEXTURE.put(EggState.TWO_YOLKS, new Identifier("textures/entity/player/eggsplash/two_yolks.png"));
        TEXTURE.put(EggState.THREE_YOLKS, new Identifier("textures/entity/player/eggsplash/three_yolks.png"));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isInvisible()){
            if (((IEgg) entity).getEgged()){
                renderModel(this.getContextModel(), TEXTURE.get(((IEgg)entity).getTexture()), matrices, vertexConsumers, light, entity, -1, -1, -1);
            }
        }
    }
}
