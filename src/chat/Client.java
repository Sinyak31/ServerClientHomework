package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Client {
    final static int PORT = 8088;
    final static String IP = "25.51.163.238";

    public static void main(String[] args) throws IOException {
        try (var socket = new Socket("localhost", PORT);//создаем сокет клиента
             var inputStream = new DataInputStream(socket.getInputStream());//поток ввода
             var outputStream = new DataOutputStream(socket.getOutputStream());//поток вывода
             var scanner = new Scanner(System.in)) {

            System.out.println("Chat started...");
            try {
                while (scanner.hasNextLine()) {
                    outputStream.writeUTF(scanner.nextLine());//запрос на сервер.
                    System.out.println(currentTime()+ " Пользователь 2 :" + inputStream.readUTF());//читаем ответ от сервера.
                }
            }
            catch (EOFException e){
                System.out.println("Chat closed...");
            }

        }
    }
    private static String currentTime() {
        return LocalTime.now().getHour()+" : "+LocalTime.now().getMinute()+ " : "+ LocalTime.now().getSecond() +" ";
    }
}
