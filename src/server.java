import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server
{
    ServerSocket serv;
    Socket socket;
    BufferedReader br;
    PrintWriter pw;
    public server()
    {
        try {
            serv=new ServerSocket(9090);
            System.out.println("Connecting to client");
            System.out.println("Waiting...");
           socket= serv.accept();
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream());
            startreading();
            startwritng();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public void startreading()
    {
        Runnable r1=()->
        {
            System.out.println("Reader started");
            try
            {
                while (!socket.isClosed())
                {
                    String msg=br.readLine();
                    if (msg.equals("exit"))
                    {
                        System.out.println("Client exit");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: "+msg);

                }

            }catch (Exception e)
            {
                System.out.println("Connection closed");
            }
        };
        new Thread(r1).start();
    }
    public void startwritng()
    {
        Runnable r2=()->
        {
            System.out.println("Writer started");
            try {
                while (!socket.isClosed())
                {
                    BufferedReader wr=new BufferedReader(new InputStreamReader(System.in));
                    String content= wr.readLine();
                    pw.println(content);
                    pw.flush();
                    if(content.equals("exit"))
                    {
                        System.out.println("closing connection..");
                        socket.close();
                        break;
                    }
                }
            }catch (Exception e)
            {
                System.out.println("Connection closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args)
    {
        System.out.println("this is Sever side");
     new server();

    }
}
