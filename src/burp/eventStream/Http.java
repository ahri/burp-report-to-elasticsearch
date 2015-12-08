package burp.eventStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public interface Http
{
    void request(String host, int port, String method, String location, String body);

    class SocketBased implements Http
    {
        private final Output output;

        public SocketBased(Output output)
        {
            this.output = output;
        }

        @Override
        public void request(String host, int port, final String method, String location, String body)
        {
            try
            {
                Socket soc = new Socket(host, port);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

                out.write(method + " " + location + " HTTP/1.1\r\nHost: " + host + "\r\nConnection: close\r\nContent-Type: application/json; charset=utf-8\r\nContent-length: " + body.getBytes().length + "\r\n\r\n" + body);
                out.flush();

                soc.close();
            }
            catch (UnknownHostException e)
            {
                output.printError("ERROR: unknown host " + e.getMessage());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public interface Output
        {
            void printError(String str);
        }
    }
}
