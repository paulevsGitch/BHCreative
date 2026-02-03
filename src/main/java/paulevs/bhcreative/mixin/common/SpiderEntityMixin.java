package paulevs.bhcreative.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.monster.MonsterEntity;
import net.minecraft.entity.living.monster.SpiderEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpiderEntity.class)
public class SpiderEntityMixin extends MonsterEntity {

    public SpiderEntityMixin(Level level) {
        super(level);
    }

    @WrapOperation(
            method = "getAttackTarget",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/level/Level;getClosestPlayerTo(Lnet/minecraft/entity/Entity;D)Lnet/minecraft/entity/living/player/PlayerEntity;"
            )
    )
    protected PlayerEntity creative_getAttackTargetClosestPlayer(Level instance, Entity entity, double maxDistance, Operation<PlayerEntity> original) {
        PlayerEntity player = original.call(instance, entity, maxDistance);

        if (player != null && player.creative_isCreative()) {
            return null;
        } else {
            return player;
        }
    }
}
