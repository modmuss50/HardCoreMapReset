package modmuss50.hcmr.voidWorld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;

public class WorldProviderVoid extends WorldProviderSurface {

	@Override
	public BlockPos getRandomizedSpawnPoint() {
		return new BlockPos(getSpawnPoint());
	}

	@Override
	public BlockPos getSpawnPoint() {
		return new BlockPos(0, 96, 0);
	}
}
