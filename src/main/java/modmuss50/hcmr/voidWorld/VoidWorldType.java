package modmuss50.hcmr.voidWorld;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class VoidWorldType extends WorldType {

	public VoidWorldType() {
		super("void");
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new VoidChunkGenerator(world);
	}

	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 0;
	}

	@Override
	public boolean isCustomizable() {
		return false;
	}

	@Override
	public WorldType getWorldTypeForGeneratorVersion(int version) {
		return this;
	}
}
