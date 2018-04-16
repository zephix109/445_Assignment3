import java.io.IOException;
import java.net.DatagramSocket;
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
			DatagramSocket localSocket = new DatagramSocket();
			
			ChatReceiver chatR = new ChatReceiver(multicastSocket, group);
			ChatLocalReceiver chatLR = new ChatLocalReceiver(localSocket);
			ChatSender chatS = new ChatSender(localSocket, multicastSocket, group);

			Thread receiverThread = new Thread(chatR);
			Thread localReveiverThread = new Thread(chatLR);
			Thread senderThread = new Thread(chatS);
			receiverThread.start();
			localReveiverThread.start();
			senderThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}