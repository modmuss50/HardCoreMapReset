package modmuss50.HardCoreMapRest.server;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
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
			try {
				maps.add(packetBuffer.readStringFromBuffer(999));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(maps.size());
		PacketBuffer packetBuffer = new PacketBuffer(buf);
		for (String name : maps) {
			try {
				packetBuffer.writeStringToBuffer(name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public IMessage onMessage(PacketOpen message, MessageContext ctx) {
		FMLClientHandler.instance().showGuiScreen(new GuiServerList(message.maps));
		return null;
	}


}
