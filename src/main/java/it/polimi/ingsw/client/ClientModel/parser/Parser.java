package it.polimi.ingsw.client.ClientModel.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonStreamParser;

import java.io.*;

public abstract class Parser {
    protected String filePath;
    protected InputStream inputStream;
    protected Reader reader;
    protected Gson gson;
    protected JsonStreamParser parser;



    public Parser(String filePath){
        this.filePath = filePath;
        try {
            File input = new File(this.filePath);
            this.inputStream = new FileInputStream(input);
            this.reader = new InputStreamReader(this.inputStream);
            this.gson = new GsonBuilder().create();
            this.parser = new JsonStreamParser(this.reader);

        } catch(FileNotFoundException e){
            System.err.println("Exception: input file not found");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            this.inputStream.close();
        } catch (IOException e){
            System.out.println("Exception occured while closing file");
            e.printStackTrace();
        }
    }
}
