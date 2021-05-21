package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.MessageType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSetUp {

    public void initialSetUp(Socket socket, Scanner scanner, DataInputStream fromServer, DataOutputStream toServer, BufferedReader buffer, Queue<Message> clientQueue) {

        MessageFactory factory = new MessageFactory();

        Runnable dequeue = new Runnable() {
            @Override
            public void run() {
                Message dequeueMessage;
                while(true){
                    if(!clientQueue.isEmpty()) {
                        dequeueMessage = clientQueue.poll();
                        System.out.println(dequeueMessage.toString());
                        dequeueMessage.clientProcess(toServer);
                    }
                }
            }
        };

        Runnable enqueue = new Runnable() {
            @Override
            public void run() {
                String received;
                while (true) {
                    try {
                        if(buffer.ready()) {
                            received = fromServer.readUTF();
                            Message message = factory.returnMessage(received);
                            clientQueue.add(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(dequeue);
        Thread t2 = new Thread(enqueue);

        t1.start();
        t2.start();
    }

}
