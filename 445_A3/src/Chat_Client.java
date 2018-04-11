import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat_Client {

	public static void main(String[] args) {
		
		try {
			DatagramSocket socket = new DatagramSocket(5555);
			ChatReceiver chatR = new ChatReceiver(socket);
	        ChatSender chatS = new ChatSender(socket);
	        Thread receiverThread = new Thread(chatR);
	        Thread senderThread = new Thread(chatS);
	        receiverThread.start(); 
	        senderThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
        
	}

}

class ChatSender implements Runnable {

	final static String ip_address = "127.0.0.1";
	final static int port = 5555;
	private DatagramSocket senderSocket;
	String username;

	ChatSender(DatagramSocket s) {
        senderSocket = s;
    }

	@Override
	public void run() {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome, please enter your name: ");
		try {
			username = in.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        while (true) {
        	read_text_from_user_input(in);
        }
	}
	
	private void buildMessage(String s) throws Exception {
		byte buf[] = s.getBytes();
		InetAddress ipAddr = InetAddress.getByName(ip_address);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ipAddr, port);
		senderSocket.send(packet);
	}
	
	public void read_text_from_user_input(BufferedReader in) {
		try {
        	String line = in.readLine();
        	if(line.equals("quit")) {
        		System.out.println("Closing chatbox");
        		System.exit(0);
        	}
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            String formattedDate = sdf.format(date);                
            buildMessage(formattedDate + " [" + username + "]: " + line);
        } catch(Exception e) {
            System.err.println(e);
        }
	}
}

class ChatReceiver implements Runnable {

	DatagramSocket receiverSocket;
    byte buffer[];
    
    ChatReceiver(DatagramSocket s) {
        receiverSocket = s;
        buffer = new byte[1024];
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                receiverSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                print(received);
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public void print(String receivedStr) {
    	System.out.println(receivedStr);
    }

}
