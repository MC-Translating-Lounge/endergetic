package com.minecraftabnormals.endergetic.common.entities.eetle.ai.glider;

import com.minecraftabnormals.endergetic.common.entities.eetle.GliderEetleEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GliderEetleTakeoffGoal extends Goal {
	private final GliderEetleEntity glider;
	private int ticksPassed;

	public GliderEetleTakeoffGoal(GliderEetleEntity glider) {
		this.glider = glider;
	}

	@Override
	public boolean shouldExecute() {
		GliderEetleEntity glider = this.glider;
		if (!glider.isGrounded() && !glider.isFlying() && glider.isNoEndimationPlaying()) {
			return glider.canFly() && glider.getRNG().nextFloat() < 0.05F || !glider.isOnGround() && willFallFar(glider);
		}
		return false;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !this.glider.isGrounded() && this.ticksPassed < 5 && this.glider.canFly();
	}

	@Override
	public void startExecuting() {
		this.glider.setFlying(true);
	}

	@Override
	public void resetTask() {
		this.ticksPassed = 0;
	}

	@Override
	public void tick() {
		this.ticksPassed++;
	}

	private static boolean willFallFar(GliderEetleEntity gliderEetleEntity) {
		World world = gliderEetleEntity.world;
		BlockPos.Mutable mutable = gliderEetleEntity.getPosition().toMutable();
		int startY = mutable.getY();
		for (int i = 0; i < 8; i++) {
			mutable.setY(startY - i);
			if (world.isTopSolid(mutable, gliderEetleEntity)) {
				return false;
			}
		}
		return true;
	}
}