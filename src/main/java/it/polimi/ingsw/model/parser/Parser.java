package it.polimi.ingsw.model.parser;

import com.google.gson.*;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Main parser class extended by all parsers.
 */
public abstract class Parser {
    /*
    protected String filePath;
    protected InputStream inputStream;
    protected Reader reader;
    protected Gson gson;
    protected JsonStreamParser parser;

     */
    protected InputStreamReader reader;
    protected JsonStreamParser parser;

    /*

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

     */

    public void close(){
        try {
            this.reader.close();
        } catch (IOException e){
            System.err.println("Exception occured while closing file");
            e.printStackTrace();
        }
    }
}
