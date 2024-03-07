import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("server ip", 5000);

        new Writeth(client).start();  // server for write
        new Readth(client).start(); // thread for read
    }
}

class Writeth extends Thread implements Runnable { // write thread
    private Socket client;

    public Writeth(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                out.println(stdIn.readLine());
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Readth extends Thread implements Runnable{ // read thread
    private Socket client;
    
    public Readth(Socket client) {
        this.client = client;
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            while (true) {
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}