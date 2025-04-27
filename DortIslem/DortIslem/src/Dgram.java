/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 13.Mar.2019, 10:58:20 
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dgram {

    public static DatagramPacket toDatagram(String s, InetAddress destIA, int destPort) {
        //byte[] buf = new byte[s.length() + 1];
        //s.getBytes(0, s.length(), buf, 0);
        byte[] buf = s.getBytes();
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }
    
    public static DatagramPacket toDatagram(byte[] buf, InetAddress destIA, int destPort) {
        //byte[] buf = new byte[s.length() + 1];
        //s.getBytes(0, s.length(), buf, 0);
        
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }
    
    public static DatagramPacket toDatagram(Islem is, InetAddress destIA, int destPort) {
        //byte[] buf = new byte[s.length() + 1];
        //s.getBytes(0, s.length(), buf, 0);
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(is);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            return new DatagramPacket(yourBytes, yourBytes.length, destIA, destPort);
        } catch (IOException ex) {
            // ignore close exception
            return null;
        }

    }

    public static String toString(DatagramPacket p) {
        return new String(p.getData(), 0, p.getLength());
    }

    public static Islem toIslem(DatagramPacket p) {

        ByteArrayInputStream bis = new ByteArrayInputStream(p.getData());
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (Islem) o;
        } catch (IOException ex) {
            // ignore close exception
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
}
