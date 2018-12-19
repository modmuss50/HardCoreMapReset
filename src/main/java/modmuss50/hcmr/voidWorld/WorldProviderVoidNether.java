package modmuss50.hcmr.voidWorld;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderVoidNether extends WorldProviderHell {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new VoidChunkGeneratorNether(world, true, getSeed());
	}
}
