package random_generator;

import java.io.*;
import java.net.ServerSocket;
import java.util.Date;

public class Server {
    public static void main(String[] args) {

        String nameClient = null;

        Date timeConnection = null;

        int countQuote = 0;

        Date timeDisconnection = null;



            try (var serverSocket = new ServerSocket(8080);
                 var socket = serverSocket.accept();
                 var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 PrintWriter out = new PrintWriter(writer, true)
            ) {
                timeConnection = new Date();
                nameClient = socket.toString();
                System.out.println("Соединение установлено." + socket.getLocalSocketAddress());
                while (true) {
                    out.println("Введите логин и пароль");
                    String name = reader.readLine();
                    String password = reader.readLine();
                    if (Utils.setPassword(name, password)) {
                        out.println("ok");
                        break;
                    } else {
                        out.println("Повторите ввод логина и пароля.");
                    }
                }


                String request = null;//запрос от клиента, то что он пишет

                String randomPhrase = null;//фраза, которую генерирует метод.

                while (true) {
                    randomPhrase = Utils.getRandomPhrase();//присваиваем фразу
                    out.println(randomPhrase);//отправляем сообщением данную фразу
                    countQuote++;
                    request = reader.readLine();
                    if (request.equalsIgnoreCase("stop")) {

                        break;
                    }
                    System.out.println(request);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        timeDisconnection = new Date();
        System.out.println("Client name :"+nameClient);
        System.out.println("Time connection :"+timeConnection);
        System.out.println("Count quotes :"+countQuote);
        System.out.println("Time disconnection :"+timeDisconnection);

    }

}
