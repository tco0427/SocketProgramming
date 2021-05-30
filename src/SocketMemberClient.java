import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class SocketMemberClient {
    public static void main(String[] args) throws IOException {
        Socket socket=null;

        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;

        InputStream inputStream=null;
        DataInputStream dataInputStream=null;

        Scanner scanner=null;

        try{
            socket = new Socket("localhost",9000);
            System.out.println("서버 연결 완료!");

            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);

            scanner=new Scanner(System.in);
            //---------------//
            String receviedMessage = dataInputStream.readUTF();
            System.out.print(receviedMessage);  //로그인? or 회원가입?

            //로그인인지 회원가입인지 입력
            String input = scanner.nextLine();
            dataOutputStream.writeUTF(input);
            dataOutputStream.flush();

            receviedMessage=dataInputStream.readUTF();
            if(receviedMessage.equals("에러")){
                System.out.println("에러");
            }else if(receviedMessage.equals("회원가입")){
                System.out.print("아이디>> ");
                input=scanner.nextLine();
                dataOutputStream.writeUTF(input);
                System.out.print("비밀번호>> ");
                input=scanner.nextLine();
                dataOutputStream.writeUTF(input);


                receviedMessage=dataInputStream.readUTF(); //false or true
                if(receviedMessage.equals("false")){
                    System.out.println("회원가입 실패!");
                }else{
                    System.out.println("회원가입에 성공하였습니다!");
                }
            }else if(receviedMessage.equals("로그인")){
                System.out.print("아이디>> ");
                input=scanner.nextLine();
                dataOutputStream.writeUTF(input);
                System.out.print("비밀번호>> ");
                input=scanner.nextLine();
                dataOutputStream.writeUTF(input);

                receviedMessage=dataInputStream.readUTF(); //false of true
                if(receviedMessage.equals("false")){
                    System.out.println("로그인 실패!");
                }else{
                    System.out.println("로그인에 성공하였습니다.");

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
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
