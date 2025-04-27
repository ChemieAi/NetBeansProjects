
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 13.Mar.2019, 10:58:47 
 */
public class ChatterServer {

    static final int INPORT = 1711;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
   // Can listen & send on the same socket:
    private DatagramSocket socket;
    
    public ChatterServer() {
        try {
            socket = new DatagramSocket(INPORT);
            System.out.println("Server started");
            while(true) {
                // Block until a datagram appears:
                socket.receive(dp);
                String rcvd = Dgram.toString(dp) +
                        ", from address: " + dp.getAddress() +
                        ", port: " + dp.getPort();
                System.out.println("Server: "+ rcvd);
                String echoString = "Echoed: " + rcvd;
                // Extract the address and port from the
                // received datagram to find out where to
                // send it back:
                DatagramPacket echo = Dgram.toDatagram(echoString, dp.getAddress(), dp.getPort());
                socket.send(echo);
            }
        } catch(SocketException e) {
            System.err.println("Can't open socket");
            System.exit(1);
       } catch (IOException ex) { 
            Logger.getLogger(ChatterServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } // metot sonu
    public static void main(String[] args) {
       new ChatterServer();
    }

}
