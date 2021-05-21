package it.polimi.ingsw.client;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.MessageType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSetUp {

    public void initialSetUp(Socket socket, Scanner scanner, DataInputStream fromServer, DataOutputStream toServer, BufferedReader buffer) {

        MessageFactory factory = new MessageFactory();

        Queue<Message> clientQueue = new LinkedList<>();

        String received;
        while (true) {
            try {
                if(fromServer.available() > 0) {
                    received = fromServer.readUTF();
                    Message message;
                    try {
                        message = factory.returnMessage(received);
                    } catch (JsonSyntaxException e){
                        message = null;
                    }
                    if(message != null) {
                        message.clientProcess(toServer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        Runnable enqueue = new Runnable() {
            @Override
            public void run() {
                String received;
                while (true) {
                    try {
                        if(fromServer.available() > 0) {
                            received = fromServer.readUTF();
                            Message message;
                            try {
                                message = factory.returnMessage(received);
                            } catch (JsonSyntaxException e){
                                message = null;
                            }
                            if(message != null) {
                                message.clientProcess(toServer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(enqueue);
        t1.start();

        /*
        Message dequeueMessage;
        while(true){
            dequeueMessage = clientQueue.poll();
            System.out.println("extracting from queue:" + dequeueMessage.toString());
            dequeueMessage = clientQueue.poll();
            if(dequeueMessage!= null) {
                System.out.println("extracting from queue:" + dequeueMessage.toString());
                dequeueMessage.clientProcess(toServer);
            }
        }

         */
    }

}
