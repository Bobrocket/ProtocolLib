package com.comphenix.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.PacketType.Sender;
import com.comphenix.protocol.compat.netty.independent.NettyProtocolRegistry;
import com.comphenix.protocol.injector.netty.ProtocolRegistry;

public class PacketTypeTest {

	@BeforeClass
	public static void initializeReflection() {
		BukkitInitialization.initializePackage();
	}

	@Test
	public void testFindCurrent() {
		assertEquals(PacketType.Play.Client.STEER_VEHICLE, PacketType.findCurrent(Protocol.PLAY, Sender.CLIENT, "SteerVehicle"));
	}

	@Test
	public void ensureAllExist() {
		boolean missing = false;
		ProtocolRegistry registry = new NettyProtocolRegistry();
		Map<PacketType, Class<?>> lookup = registry.getPacketTypeLookup();
		for (Entry<PacketType, Class<?>> entry : lookup.entrySet()) {
			PacketType type = entry.getKey();
			Class<?> clazz = entry.getValue();

			if (type.isDynamic()) {
				System.err.println("Packet " + clazz + " does not have a corresponding PacketType!");
				missing = true;
				
			}
			//assertFalse("Packet " + clazz + " does not have a corresponding PacketType!", type.isDynamic());
		}

		assertFalse("There are packets that aren\'t accounted for!", missing);
	}
}