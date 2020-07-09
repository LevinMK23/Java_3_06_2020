package Chat;

import Chat.Front.Chat;
import lombok.Data;

import java.io.*;

public  class Socket extends java.net.Socket {
   public final static String ip ="localhost";
   public final static  int port = 13013;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket instance=this;
    public void write_in(String message) throws IOException {
        if (writer==null)writer=new PrintWriter(this.getOutputStream(),true );
        writer.write(message);
    }
    public BufferedReader getReader() throws IOException {
        if (reader==null)reader=new BufferedReader(new InputStreamReader(instance.getInputStream()));
        return reader;
    }
    public Socket() throws IOException {
        super(ip,port);
    }

}
