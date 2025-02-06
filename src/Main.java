import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Main {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objIn;

    public void start(int port) throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        InetAddress serverAddress = serverSocket.getInetAddress();
        System.out.println("Server started at " + serverAddress.getHostAddress() + ":" + port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        objIn = new ObjectInputStream(clientSocket.getInputStream());

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                out.println("good bye");
                break;
            }
            out.println(inputLine);
        }

        Array[] arr = (Array[]) objIn.readObject();
        System.out.println("Received Object: " + Arrays.toString(arr));

        String message = in.readLine();
        System.out.println("Received Message: " + message);

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Main server=new Main();
        server.start(6666);
    }
}