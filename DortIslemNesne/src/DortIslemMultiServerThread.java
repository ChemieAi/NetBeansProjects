/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 06.Mar.2019, 13:29:02 
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DortIslemMultiServerThread extends Thread {
    private Socket socket = null;

    public DortIslemMultiServerThread(Socket socket) {
	super("KKMultiServerThread");
	this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("thread başlatıldı");
	try {
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Object obj; 
            String outputLine;
            
            try {
                while ((obj = in.readObject()) != null) {
                    
                    //System.out.println(inputLine);
                    outputLine = calculate(obj);
                    out.println(outputLine);
                    
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DortIslemMultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Client bağlantıyı kesti");
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private String calculate(Object obj) {
        
        // if (obj instance of Islem)
        Islem is=(Islem)obj;
        
        double sonuc;
        
        try{
            if (is.getOperator().equals("+")){
                sonuc=is.getOp1() + is.getOp2();
            }else if (is.getOperator().equals("-")){
                sonuc=is.getOp1() - is.getOp2();
            }else if (is.getOperator().equals("*")){
                sonuc=is.getOp1() * is.getOp2();
            }else if (is.getOperator().equals("/")){
                sonuc=is.getOp1() / is.getOp2();
            }else{
                return "Hata: Operatör hatası";
            }
            
            return Double.toString(sonuc);
        }catch(Exception e){
            return "Hata: "+e.toString();
        }
        
    }
}

