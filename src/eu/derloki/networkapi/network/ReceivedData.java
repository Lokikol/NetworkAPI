package eu.derloki.networkapi.network;

import java.net.InetAddress;

public class ReceivedData {
	public byte[] data = new byte[0];
	public int length = 0;
	public InetAddress address = null;
	public int port = 0;
}
