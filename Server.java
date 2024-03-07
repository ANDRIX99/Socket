import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static Vector<PrintWriter> clientWriters = new Vector<>();
    public static void main(String[] args) { 
        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while(true) {
                Socket clientSocket = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class clientHandler extends Thread {
        private Socket client;
        private PrintWriter writer;
    
        public ClientHandler(Socket client) {
            this.client = client;
        }
    
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream(), true));
                this.writer = new PrintWriter(this.client.getOutputStream(), true);
    
                synchronized (clientWriters) {
                    clientWriters.add(write);
                }

                String line;
                while((line = reader.readLine()) != null) {
                    System.out.println("Message from " + this.client.getInetAddress() + ": " + line);
                    synchronized (clientWriters) {
                        for(PrintWriter clientWrite : clientWriters) {
                            clientWrite.println(this.client.getInetAddress() + ":" + line);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if(writer != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(writer);
                    }
                }

                try {
                    this.client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


