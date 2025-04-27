/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 13.Mar.2019, 10:58:20 
 */
import java.net.*;

public class Dgram {

    public static DatagramPacket toDatagram(String s, InetAddress destIA, int destPort) {
        //byte[] buf = new byte[s.length() + 1];
        //s.getBytes(0, s.length(), buf, 0);
        byte[] buf = s.getBytes();
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }

    public static String toString(DatagramPacket p) {
        return new String(p.getData(), 0, p.getLength());
    }
} 

