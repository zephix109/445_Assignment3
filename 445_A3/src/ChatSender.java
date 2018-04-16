import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ChatSender implements Runnable {

	final static String ip_address = "255.255.255.255";
	final static int port = 5555;
	private InetAddress group; 
	private DatagramSocket senderSocket;
	String username;

	ChatSender(DatagramSocket s, InetAddress g) { 
        senderSocket = s; 
        group = g; 
    } 

	@Override
	public void run() {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome, please enter your name: ");
		try {
			username = in.readLine();
			String command = "JOIN";
			buildMessage("", command);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
        while (true) {
        	read_text_from_user_input(in);
        }
	}
	
	public void read_text_from_user_input(BufferedReader in) {
		try {
        	String line = in.readLine();
        	String command = "TALK";
        	if(line.equals("/leave")) {
        		command = "LEAVE";
        		buildMessage("", command);
        		localMessage("", "QUIT");
        		System.exit(0);
        	} else if(line.equals("/who")) {		//TODO needs to be made local only
        		command = "WHO";
        		localMessage(line, command);
        		command = null;
        	}
            buildMessage(line, command);
        } catch(Exception e) {
            System.err.println(e);
        }
	}
	
	private void localMessage(String line, String command) throws Exception {
		String message = "user:" + username + "\ncommand:" + command + "\nmessage:" + line + "\n\n";
		byte buf[] = message.getBytes();
		InetAddress ipAddr = InetAddress.getByName("localhost");
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ipAddr, port);
		senderSocket.send(packet);
	}
	
	private void buildMessage(String line, String command) throws Exception {
		if (command == null) return;
		String message = "user:" + username + "\ncommand:" + command + "\nmessage:" + line + "\n\n";
		byte buf[] = message.getBytes();
		InetAddress ipAddr = InetAddress.getByName(ip_address);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ipAddr, port);
		senderSocket.send(packet);
	}
}
