package modmuss50.HardCoreMapReset.server.packets;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.IOException;

public abstract class SimplePacket {
    protected EntityPlayer player;
    protected byte mode;

    public SimplePacket(EntityPlayer _player) {
        player = _player;
    }

    @SuppressWarnings("unused")
    public SimplePacket() {
        player = null;
    }

    public static String readString(ByteBuf in) throws IOException {
        byte[] stringBytes = new byte[in.readInt()];
        in.readBytes(stringBytes);
        return new String(stringBytes, Charsets.UTF_8);
    }

    public static void writeString(String string, ByteBuf out) throws IOException {
        byte[] stringBytes;
        stringBytes = string.getBytes(Charsets.UTF_8);
        out.writeInt(stringBytes.length);
        out.writeBytes(stringBytes);
    }

    public static World readWorld(ByteBuf in) throws IOException {
        return DimensionManager.getWorld(in.readInt());
    }

    public static void writeWorld(World world, ByteBuf out) throws IOException {
        out.writeInt(world.provider.getDimensionId());
    }

    public static EntityPlayer readPlayer(ByteBuf in) throws IOException {
        if (!in.readBoolean())
            return null;
        World playerWorld = readWorld(in);
        return playerWorld.getPlayerEntityByName(readString(in));
    }

    public static void writePlayer(EntityPlayer player, ByteBuf out) throws IOException {
        if (player == null) {
            out.writeBoolean(false);
            return;
        }
        out.writeBoolean(true);
        writeWorld(player.worldObj, out);
        writeString(player.getName(), out);
    }

    public void writePacketData(ByteBuf out) throws IOException {
        out.writeByte(mode);
        writePlayer(player, out);
        writeData(out);
    }

    public abstract void writeData(ByteBuf out) throws IOException;

    public void readPacketData(ByteBuf in) throws IOException {
        mode = in.readByte();
        player = readPlayer(in);
        readData(in);
    }

    public abstract void readData(ByteBuf in) throws IOException;

    public abstract void execute();

    public void sendPacketToAllPlayers() {
        ChannelHandler.sendPacketToAllPlayers(this);
    }

}
