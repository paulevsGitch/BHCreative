package paulevs.bhcreative.mixin.common;

import net.minecraft.entity.BaseEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Vec3f;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.bhcreative.interfaces.CreativePlayer;
import paulevs.bhcreative.mixin.client.LivingEntityAccessor;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin extends LivingEntity implements CreativePlayer {
	@Shadow public abstract void tickRiding();
	
	@Unique private final Vec3f creative_flightSpeed = Vec3f.make(0, 0, 0);
	@Unique private boolean creative_isCreative;
	@Unique private boolean creative_isFlying;
	
	public PlayerBaseMixin(Level arg) {
		super(arg);
	}
	
	@Override
	public boolean creative_isCreative() {
		return creative_isCreative;
	}
	
	@Override
	public void creative_setCreative(boolean creative) {
		this.creative_isCreative = creative;
	}
	
	@Override
	public boolean creative_isFlying() {
		return creative_isFlying;
	}
	
	@Override
	public void creative_setFlying(boolean flying) {
		if (flying && !this.creative_isFlying) {
			creative_flightSpeed.x = velocityX;
			creative_flightSpeed.y = velocityY;
			creative_flightSpeed.z = velocityZ;
		}
		else if (!flying && this.creative_isFlying) {
			creative_flightSpeed.x = 0;
			creative_flightSpeed.y = 0;
			creative_flightSpeed.z = 0;
		}
		this.creative_isFlying = flying;
	}
	
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void creative_damage(BaseEntity target, int amount, CallbackInfoReturnable<Boolean> info) {
		if (this.creative_isCreative()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	@Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
	private void creative_applyDamage(int damageAmount, CallbackInfo info) {
		if (this.creative_isCreative()) {
			info.cancel();
		}
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void creative_writeCustomDataToTag(CompoundTag tag, CallbackInfo info) {
		tag.put("Creative", creative_isCreative());
		tag.put("Flying", creative_isFlying());
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void creative_readCustomDataFromTag(CompoundTag tag, CallbackInfo info) {
		creative_setCreative(tag.getBoolean("Creative"));
		creative_setFlying(tag.getBoolean("Flying"));
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void creative_tick(CallbackInfo info) {
		if (!this.creative_isCreative()) return;
		if (!this.creative_isFlying()) return;
		
		if (this.isSleeping() || this.vehicle != null) {
			this.creative_setFlying(false);
			return;
		}
		
		if (this.onGround) {
			this.creative_setFlying(false);
			return;
		}
		
		LivingEntityAccessor entity = (LivingEntityAccessor) this;
		
		float front = entity.creative_getFrontMovement();
		float right = entity.creative_getRightMovement();
		double angle = Math.toRadians(this.yaw);
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		float dx = (front * cos - right * sin);
		float dz = (right * cos + front * sin);
		
		creative_flightSpeed.x = MathHelper.lerp(0.15, creative_flightSpeed.x, dx * 0.4);
		creative_flightSpeed.z = MathHelper.lerp(0.15, creative_flightSpeed.z, dz * 0.4);
		
		boolean sneaking = this.isChild();
		
		dx = 0;
		if (jumping) dx += 0.4F;
		if (sneaking) dx -= 0.4F;
		
		creative_flightSpeed.y = MathHelper.lerp(0.2, creative_flightSpeed.y, dx);
		
		this.velocityX = creative_flightSpeed.x;
		this.velocityY = creative_flightSpeed.y;
		this.velocityZ = creative_flightSpeed.z;
	}
}
