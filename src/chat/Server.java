package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalTime;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        try (var serverSocket = new ServerSocket(8088);//создаем сокет сервера.
             var socket = serverSocket.accept();//открываем сам сокет.
             var inputStream = new DataInputStream(socket.getInputStream());//получили стрим для чтения данных.
             var outputStream = new DataOutputStream(socket.getOutputStream());//получили стрим для передачи данных.
             var scanner = new Scanner(System.in)) {//из сокета получаем поток.

            var request = inputStream.readUTF();//читаем то что пишет пользователь.
            try {
                while (!request.equals("exit")) {
                    System.out.println(currentTime() + "Пользователь 1 :" + request);
                    outputStream.writeUTF(scanner.nextLine());//ответ клиенту.
                    request = inputStream.readUTF();
                }
            }
            catch (EOFException e) {
                System.out.println("Chat ended...");
            }
        }
    }
    private static String currentTime() {
        return LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+ ":"+ LocalTime.now().getSecond()+ " ";
    }
}
