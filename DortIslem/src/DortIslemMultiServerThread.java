/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 06.Mar.2019, 13:29:02 
 */
import java.net.*;
import java.io.*;
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
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine,outputLine;
            
	    while ((inputLine = in.readLine()) != null && !inputLine.equalsIgnoreCase("end")) {
                
                System.out.println(inputLine);
		outputLine = calculate(inputLine);
		out.println(outputLine);
		
	    }
            System.out.println(inputLine);
            System.out.println("Client bağlantıyı kesti");
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private String calculate(String inputLine) {
        
        String[] op=inputLine.split(",");
        double sonuc;
        
        try{
            if (op[0].equals("+")){
                sonuc=Double.parseDouble(op[1]) + Double.parseDouble(op[2]);
            }else if (op[0].equals("-")){
                sonuc=Double.parseDouble(op[1]) - Double.parseDouble(op[2]);
            }else if (op[0].equals("*")){
                sonuc=Double.parseDouble(op[1]) * Double.parseDouble(op[2]);
            }else if (op[0].equals("/")){
                sonuc=Double.parseDouble(op[1]) / Double.parseDouble(op[2]);
            }else{
                return "Hata: Operatör hatası";
            }
            
            return Double.toString(sonuc);
        }catch(Exception e){
            return "Hata: "+e.toString();
        }
        
    }
}

