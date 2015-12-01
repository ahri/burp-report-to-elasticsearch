package burp.eventStream;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public interface Http
{
    void post(String host, int port, String location, String body);

    class SocketBased implements Http
    {
        @Override
        public void post(String host, int port, String location, String body)
        {
            try
            {
                Socket soc = new Socket(host, port);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

                out.write("POST " + location + " HTTP/1.1\r\nHost: " + host + "\r\nConnection: close\r\nContent-Type: application/json; charset=utf-8\r\nContent-length: " + body.getBytes().length + "\r\n\r\n" + body);
                out.flush();

                soc.close();
            }
            catch (Throwable e)
            {
                e.printStackTrace(); // fire and forget!
            }
        }
    }
}
