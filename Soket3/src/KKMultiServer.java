
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Ozgun Yilmaz <ozgunyilmaz35@gmail.com>
 * Created on 06.Mar.2019, 13:28:22 
 */
public class KKMultiServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening)
	    new KKMultiServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }

}
