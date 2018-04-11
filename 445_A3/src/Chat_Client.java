import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Chat_Client {

	public static void main(String[] args) {
		
		DatagramSocket socket = new DatagramSocket();
        ChatReceiver chatR = new ChatReceiver(socket);
        ChatSender chatS = new ChatSender(socket);
        Thread receiverThread = new Thread(chatR);
        Thread senderThread = new Thread(chatS);
        receiverThread.start(); 
        senderThread.start();
	}

}

class ChatSender implements Runnable {

	final static String ip_address = "127.0.0.1";
	final static int port = 5555;
	private DatagramSocket senderSocket;

	ChatSender(DatagramSocket s) {
        senderSocket = s;
    }

	private void sendMessage(String s) throws Exception {
		byte buf[] = s.getBytes();
		InetAddress ipAddr = InetAddress.getByName(ip_address);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ipAddr, port);
		senderSocket.send(packet);
	}

	public void run() {
		
	}
}

class ChatReceiver implements Runnable {

	@Override
	public void run() {

	}

}
