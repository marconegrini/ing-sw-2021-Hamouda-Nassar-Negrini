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

    public void initialSetUp(Socket socket, Scanner scanner, DataInputStream fromServer, DataOutputStream toServer, BufferedReader buffer, Queue<Message> messageQueue) {

        MessageFactory factory = new MessageFactory();

        Runnable dequeue = new Runnable() {

            @Override
            public void run() {
                Message fromQueue;
                while(true){
                    if(!messageQueue.isEmpty()) {
                        fromQueue = messageQueue.poll();
                        System.out.println(fromQueue.toString());
                        fromQueue.clientProcess(toServer);
                    }
                }
            }
        };

        Runnable enqueue = new Runnable() {

            @Override
            public void run() {
                String receivedFromServer;
                while (true) {
                    try {
                        if(buffer.ready()) {
                            receivedFromServer = fromServer.readUTF();
                            Message message = factory.returnMessage(receivedFromServer);
                            messageQueue.add(message);
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
