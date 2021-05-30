import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SocketMemberServer {
    public static void main(String[] args) throws IOException {
        Map<String,String> member=new HashMap<>();

        ServerSocket serverSocket=null;
        Socket socket=null;

        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;

        InputStream inputStream=null;
        DataInputStream dataInputStream=null;

        Scanner scanner=null;

        try{
            serverSocket=new ServerSocket(9000);
            System.out.println("서버 준비 완료!");


            while(true){
                socket=serverSocket.accept();
                System.out.println("클라이언트 연결 완료");

                inputStream=socket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);

                outputStream = socket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);

                scanner=new Scanner(System.in);

                dataOutputStream.writeUTF("로그인 or 회원가입 >> ");
                dataOutputStream.flush();

                String clientMessage = dataInputStream.readUTF();
                System.out.println("clientMessage: "+clientMessage);


                if(clientMessage.equals("로그인")){
                    dataOutputStream.writeUTF("로그인");
                    dataOutputStream.flush();

                    String id=dataInputStream.readUTF();
                    String password=dataInputStream.readUTF();

                    System.out.println("id: "+id+", password: "+password);
                    if(member.containsKey(id)){
                        if(password.equals(member.get(id))){
                            dataOutputStream.writeUTF("true");
                            dataOutputStream.flush();
                        }else{
                            dataOutputStream.writeUTF("false");
                            dataOutputStream.flush();
                        }
                    }else{
                        dataOutputStream.writeUTF("false");
                        dataOutputStream.flush();
                    }
                }else if(clientMessage.equals("회원가입")){
                    dataOutputStream.writeUTF("회원가입");
                    dataOutputStream.flush();

                    String id=dataInputStream.readUTF();//아이디 입력
                    String password=dataInputStream.readUTF();//비밀번호 입력

                    System.out.println("id: "+id+", password: "+password);
                    if(member.containsKey(id)){
                        dataOutputStream.writeUTF("false");
                        dataOutputStream.flush();
                    }else{
                        member.put(id,password);
                        dataOutputStream.writeUTF("true");
                        dataOutputStream.flush();
                    }
                    System.out.println("member: "+id+", "+member.get(id));
                }else{
                    dataOutputStream.writeUTF("에러");
                    dataOutputStream.flush();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (dataOutputStream != null) dataOutputStream.close();
                if (outputStream != null) outputStream.close();
                if (dataInputStream != null) dataInputStream.close();
                if (inputStream != null) inputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
