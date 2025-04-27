
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 06.Mar.2019, 13:28:22 
 */
public class DortIslemServer {

    static final int INPORT = 1711;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
   // Can listen & send on the same socket:
    private DatagramSocket socket;
    
    public DortIslemServer() {
        try {
            socket = new DatagramSocket(INPORT);
            System.out.println("Server started");
            while(true) {
                // Block until a datagram appears:
                socket.receive(dp);
                Islem rcvd = Dgram.toIslem(dp);
                //System.out.println("Server: "+ rcvd);
                String echoString = calculate(rcvd);
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
            Logger.getLogger(DortIslemServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } // metot sonu
    
    private String calculate(Islem is) {
        
        
        double sonuc;
        
        try{
            if (is.getIsaret().equals("+")){
                sonuc=is.getOperand1()+is.getOperand2();
            }else if (is.getIsaret().equals("-")){
                sonuc=is.getOperand1()-is.getOperand2();
            }else if (is.getIsaret().equals("*")){
                sonuc=is.getOperand1()*is.getOperand2();
            }else if (is.getIsaret().equals("/")){
                sonuc=is.getOperand1()/is.getOperand2();
            }else{
                return "Hata: Operatör hatası";
            }
            
            return Double.toString(sonuc);
        }catch(Exception e){
            return "Hata: "+e.toString();
        }
        
    }
    
    public static void main(String[] args) {
       new DortIslemServer();
    }
}