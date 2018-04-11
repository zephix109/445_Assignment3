import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        while (true) {
        	read_text_from_user_input(in);
        }
	}
	
	public void read_text_from_user_input(BufferedReader in) {
		try {
        	String line = in.readLine();
        	if(line.equals("quit")) {
        		System.out.println("Closing chatbox");
        		System.exit(0);
        	}
            buildMessage(line);
        } catch(Exception e) {
            System.err.println(e);
        }
	}
	
	private void buildMessage(String line) throws Exception {
		String message = "user:" + username + "\nmessage:" + line + "\n\n";
		byte buf[] = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port); 
	    senderSocket.send(packet); 
	}
}
