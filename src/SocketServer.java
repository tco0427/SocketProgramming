import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException{
        SocketServer socketServer = new SocketServer();
        socketServer.run();
    }

    public void run() throws IOException{
        BufferedWriter bw=null;
        ServerSocket server=null;
        Socket socket=null;
        try {
            int port = 18501;
            server = new ServerSocket(port);
            System.out.println("서버가 준비되었습니다.");
        }catch(IOException e){
                e.printStackTrace();
        }
        while(true) {
            try {
                socket = server.accept();         // 계속 기다리고 있다가 클라이언트가 접속하면 통신할 수 있는 소켓 반환
                System.out.println(socket.getInetAddress() + "로 부터 연결요청이 들어옴");

                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                InputStream is = socket.getInputStream();

                byte[] bytes = new byte[1024];

                is.read(bytes);

                String data = new String(bytes);
                data=data.replaceAll("(\r|\n|\r\n|\n\r)","");  //엔터 제거
                data=data.trim();
                System.out.println("input: "+data);

                String[] inputs = data.split(" ");

                if (inputs.length != 3) {
                    bw.write("에러");
                    bw.flush();
                } else {
                    Integer result = null;
                    int input1 = 0, input2 = 0;
                    boolean checkFlag = false;
                    try {
                        input1 = Integer.parseInt(inputs[0]);
                        input2 = Integer.parseInt(inputs[2]);
                    } catch (NumberFormatException e) {
                        bw.write("에러");
                        bw.flush();
                        checkFlag = true;
                    }
                    if (checkFlag == false) {
                        if (inputs[1].equals("+")) {
                            result = input1 + input2;
                        } else if (inputs[1].equals("-")) {
                            result = input1 - input2;
                        } else if (inputs[1].equals("x")) {
                            result = input1 * input2;
                        } else if (inputs[1].equals("/")) {
                            if(input2==0){
                                result=0;
                            }else {
                                result = input1 / input2;
                            }
                        }
                        bw.write(String.valueOf(result));
                        bw.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) try {
                    bw.close();
                } catch (IOException e) {
                }
                if (socket != null) try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}