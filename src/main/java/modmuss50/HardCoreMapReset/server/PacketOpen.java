package modmuss50.HardCoreMapReset.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;


public class PacketOpen implements IMessage, IMessageHandler<PacketOpen, IMessage> {

    ArrayList<String> maps = new ArrayList<String>();
    int amount;

    public PacketOpen() {
    }

    ;

    public PacketOpen(ArrayList<String> names) {
        for (String name : names) {
            this.maps.add(name);
            System.out.println(name);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        amount = buf.readInt();
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        System.out.println(amount);
        for (int i = 0; i < amount; i++) {
            maps.add(packetBuffer.readStringFromBuffer(999));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(maps.size());
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        for (String name : maps) {
            packetBuffer.writeString(name);
        }
    }


    @Override
    public IMessage onMessage(PacketOpen message, MessageContext ctx) {
        FMLClientHandler.instance().showGuiScreen(new GuiServerList(message.maps));
        return null;
    }


}
