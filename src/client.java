import java.io.*;
import java.net.*;

public class client {

    Socket socket;
    BufferedReader br;
    PrintWriter pw;

    public client() {
        try {
            System.out.println("Connecting to server");
            socket = new Socket("127.0.0.1", 9090);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            read();
            write();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        Runnable r1 = () ->
        {
            System.out.println("reader started");
            try {
                while (!socket.isClosed()) {
                    String msg;
                    //TODO:convert to lowercase
                    msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server exit");
                        socket.close();
                        break;
                    }
                    System.out.println("Sever: " + msg);
                }

            } catch (Exception e) {
                System.out.println("Connection closed");
            }
        };
        new Thread(r1).start();
    }

    public void write() {
        Runnable r2 = () ->
        {
            System.out.println("Writer started");
            try {
                while (!socket.isClosed()) {
                    BufferedReader bee = new BufferedReader(new InputStreamReader(System.in));
                    String content = bee.readLine();
                    pw.println(content);
                    pw.flush();
                    if (content.equals("exit")) {
                        System.out.println("connection closing...");
                        socket.close();
                        break;

                    }
                }
            } catch (Exception e) {
                System.out.println("connection closed");
            }


        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client area");
        new client();

    }
}
