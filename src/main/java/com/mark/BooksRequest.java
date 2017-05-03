
//TODO: Get this to work.

/*

package com.mark;

import com.google.api.client.http.*;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;

import static com.mark.Controller.addBookGUI;

public class BooksRequest
        extends com.google.api.client.json.rpc2.JsonRpcRequest {

    private JsonFactory factory;
    private String key;
    private String isbn = "";
    private String shortURL;
    private String fullURL;
    private Controller controller;
    private JsonParser parser;

    public BooksRequest() throws Exception {

        this.controller = controller;

        factory = new JsonFactory() {
            @Override
            public JsonParser createJsonParser(InputStream inputStream) throws IOException {
                return null;
            }

            @Override
            public JsonParser createJsonParser(InputStream inputStream, Charset charset) throws IOException {
                return null;
            }

            @Override
            public JsonParser createJsonParser(String s) throws IOException {
                return null;
            }

            @Override
            public JsonParser createJsonParser(Reader reader) throws IOException {
                return null;
            }

            @Override
            public JsonGenerator createJsonGenerator(OutputStream outputStream, Charset charset) throws IOException {
                return null;
            }

            @Override
            public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
                return null;
            }
        };
        key = readKey();
        isbn = Controller.getISBNForAPI();
        shortURL = "https://www.googleapis.com/books/v1/volumes?q=";

        fullURL = shortURL + isbn + "&key=" + key;

        GenericUrl address = new GenericUrl(fullURL);
        HttpRequest request = new HttpRequest();

        JsonHttpContent content = new JsonHttpContent(factory, );



        Volume volume = new Volume();

        try
        {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            System.out.println("Title: " + volumeInfo.getTitle());
            System.out.println("Id: " + volume.getId());
            System.out.println("Authors: " + volumeInfo.getAuthors());
            System.out.println("date published: " + volumeInfo.getPublishedDate());
            System.out.println();

        } catch (Exception ex) {
            System.out.println("Did not work: " + ex.toString());
        }

        //InputStreamReader reader = new InputStreamReader(is);

        //Set up reader and string builder
        //BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        /*
        //Read stream to string builder. Convert to string.
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        String responseStr = responseStrBuilder.toString();

        //Parse string to JSON
        JsonStreamParser streamParser = new JsonStreamParser(responseStr);
        //Initialize JSON object
        JsonObject response = new JsonObject();
        JsonElement element;
        int x = 0;

        while (streamParser.hasNext()) {
            element = streamParser.next();
            response.add(element.toString(), element);
            x++;
        }

        //is.close();
        */


        /*//Convert to JSON object

        JsonObject newObj = new JsonObject(); //May be an array, may be an object.
        JsonObject response = newObj.getAsJsonObject(reader.toString());

        System.out.println(response.toString());
        */

        /*
    }


    private String readKey() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            String key = reader.readLine();
            return key;
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(addBookGUI, "API key file not found.");
            System.exit(-1);
        }
        return null;
    }


    public HttpTransport transport() throws IOException {

        fullURL = shortURL + isbn + "&key=" + key;
        GenericUrl address = new GenericUrl(fullURL);

        HttpTransport request;
        HttpRequestFactory newFactory = request.createRequestFactory();
        newFactory.buildGetRequest(address);
        return address;
    };
}




*/