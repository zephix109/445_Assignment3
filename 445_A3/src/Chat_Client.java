import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Chat_Client {

	public static void main(String[] args) {

		String address = "224.0.0.5";
		int port = 5555;

		try {
			InetAddress group = InetAddress.getByName(address);
			MulticastSocket multicastSocket = new MulticastSocket(port);
			multicastSocket.joinGroup(group);
			ChatReceiver chatR = new ChatReceiver(multicastSocket, group);
			ChatSender chatS = new ChatSender(multicastSocket, group);

			Thread receiverThread = new Thread(chatR);
			Thread senderThread = new Thread(chatS);
			receiverThread.start();
			senderThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}