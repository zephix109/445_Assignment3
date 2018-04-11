import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

class ChatReceiver implements Runnable {

	DatagramSocket receiverSocket;
	InetAddress group;
    byte buffer[];
    
    ChatReceiver(DatagramSocket s, InetAddress g) {
        receiverSocket = s;
        buffer = new byte[1024];
        group = g;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
            	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                receiverSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                String parsedMessage  = parseMessage(received);
            	
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
                String formattedDate = sdf.format(date);
                
                print(formattedDate + " " + parsedMessage);
            } catch(Exception e) {
            	System.err.println(e);
            }
        }
    }
    
    public String parseMessage(String received) {
    	String username = received.substring(received.indexOf("user:") + 5, received.indexOf("\n"));
    	String message = received.substring(received.indexOf("message:") + 8, received.lastIndexOf("\n\n"));
    	String parsedMessage = "[" + username + "]: " + message;
    	return parsedMessage;
    }
    
    public void print(String receivedStr) {
    	System.out.println(receivedStr);
    }

}
