import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClient{
    public static void main(String[] args) throws IOException {
        SocketClient cm = new SocketClient();
        cm.run(args);
    }

    void run(String[] args) throws IOException {
        //소켓 생성
        Socket socket = new Socket();
        //주소 생성
        SocketAddress address = new InetSocketAddress("127.0.0.1", 18501);
        //주소에 해당하는 서버랑 연결
        socket.connect(address);

        try {
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result="error";
            if(args.length!=3){
                System.out.println("인자는" + args.length+" 개가 아닌 인자는 3개여야합니다.");
            }else{
                result= args[0] + " "+args[1]+" " +args[2];

            }
            pw.print(result);
            pw.flush();
            System.out.println("결과: "+br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}