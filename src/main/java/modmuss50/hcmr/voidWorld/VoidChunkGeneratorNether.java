package modmuss50.hcmr.voidWorld;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

public class VoidChunkGeneratorNether extends ChunkGeneratorHell {

	MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
	World world;

	public VoidChunkGeneratorNether(World world, boolean generateStructures, long seed) {
		super(world, generateStructures, seed);
		this.world = world;
	}

	@Override
	public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer) {

	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();

		genNetherBridge.generate(this.world, x, z, chunkprimer);

		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();

		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte) Biome.getIdForBiome(abiome[i]);
		}

		chunk.resetRelightChecks();
		return chunk;
	}
}
