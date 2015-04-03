package modmuss50.HardCoreMapRest.server;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import modmuss50.HardCoreMapRest.server.packets.SimplePacket;

import java.io.IOException;
import java.util.ArrayList;


public class PacketOpen extends SimplePacket {

	ArrayList<String> maps = new ArrayList<String>();
	int amount;

	public PacketOpen(ArrayList<String> names) {
		this.maps.addAll(names);
	}

	@Override
	public void readData(ByteBuf buf) {
		amount = buf.readInt();
//		for (int i = 0; i < amount; i++) {
//			int nameLength = buf.readInt();
//			String name = new String(buf.readBytes(nameLength).array());
//			maps.add(name);
//		}
	}

	@Override
	public void writeData(ByteBuf buf) {
		System.out.println(maps.size());
		buf.writeInt(maps.size());
//		for(String name : maps){
//			buf.writeInt(name.length());
//			buf.writeBytes(name.getBytes());
//		}
	}





	@Override
	public void execute() {
		FMLClientHandler.instance().showGuiScreen(new GuiServerList(maps));
	}
}
