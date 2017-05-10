package com.mark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.URL;


class BooksRequest {

    public static void getByISBN(String isbn, AddBookGUI addBookGUI) {

        new BookAPIWorker(isbn, addBookGUI).execute();

    }

    private static class BookAPIWorker extends SwingWorker<Book, Void> {

        final String isbn;
        final AddBookGUI gui;

        public BookAPIWorker(String isbn, AddBookGUI addBookGUI) {
            this.isbn = isbn;
            this.gui = addBookGUI;
        }

        @Override
        protected Book doInBackground() throws Exception {
            String nIsbn = "";
            String title = "";
            String author = "";
            String Categories = "";
            int pages = 0;
            String pub = "";
            String pubDate = "";
            Book toReturn = new Book(0, nIsbn, title, author, Categories, pages, pub, pubDate, "",
                    0, null, null, 0.00, 0.00);

            String key = readKey(gui);

            if (key == null) {
                System.out.println("Fix key error. Exiting program");
            }

            String shortURL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

            String fullURL = shortURL + isbn + "&key=" + key;

            InputStream is = new URL(fullURL).openConnection().getInputStream();

            InputStreamReader isReader = new InputStreamReader(is);
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bReader.readLine()) != null) {
                builder.append(line);
            }

            //Convert to string
            String responseString = builder.toString();

            //Convert string to JSON object
            JSONArray items = null;
            JSONObject jsonObject = new JSONObject(responseString);

            try {
                items = jsonObject.getJSONArray("items");
            } catch (JSONException jse) {
                System.out.println(jse.toString());
                return toReturn;
            }


            //Get top match only
            try {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");

                try {
                    toReturn.setIsbn(volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier"));
                } catch(Exception e) {
                    System.out.println(e.toString());
                    System.out.println("ISBN info not found.");
                    //return toReturn;
                }

                try {
                    toReturn.setTitle(volumeInfo.getString("title"));
                } catch(Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Title not found.");
                    //return toReturn;
                }

                try {
                    toReturn.setPublisher(volumeInfo.getString("publisher"));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("No publisher info.");
                    //return toReturn;
                }

                try {
                    toReturn.setPubDate(volumeInfo.getString("publishedDate"));
                } catch (Exception e){
                    System.out.println(e.toString());
                    System.out.println("Error finding publish date.");
                    //return toReturn;
                }

                try {
                    toReturn.setPages(volumeInfo.getInt("pageCount"));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Error finding page count.");
                    //return toReturn;
                }

                try {
                    //Establish loops to capture multiple authors and categories
                    StringBuilder b = new StringBuilder();
                    int counter = 0;
                    for (Object x : volumeInfo.getJSONArray("authors")) {
                        b.append(volumeInfo.getJSONArray("authors").getString(counter));
                        if (!volumeInfo.getJSONArray("authors").isNull(counter + 1)) {
                            b.append(", ");
                            counter++;
                        }
                    }
                    //Send author string
                    toReturn.setAuthor(b.toString());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Error retrieving authors.");
                    //return toReturn;
                }

                try {
                    //Reset variables for next String
                    StringBuilder b = new StringBuilder();
                    int counter = 0;
                    for (Object x : volumeInfo.getJSONArray("categories")) {
                        b.append(volumeInfo.getJSONArray("categories").getString(counter));
                        if (!volumeInfo.getJSONArray("categories").isNull(counter + 1)) {
                            b.append(", ");
                            counter++;
                        }
                    }
                    //Send Categories string
                    toReturn.setCategories(b.toString());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Error retrieving Categories.");
                    //return toReturn;
                }

            } catch (JSONException jse) {
                jse.printStackTrace();
                return toReturn;
            }

            return toReturn;
        }


        @Override
        public void done() {
            try {
                Book response = get();   // get() fetches whatever doInBackground returns.
                gui.responseComplete(response);

                String nIsbn = "";
                String title = "";
                String author = "";
                String Categories = "";
                int pages = 0;
                String pub = "";
                String pubDate = "";

                StringBuilder message = new StringBuilder();

                if (response.getIsbn().equals("")) {
                    message.append("ISBN\n");
                } else if (response.getTitle().equals("")) {
                    message.append("Title\n");
                } else if (response.getAuthor().equals("")) {
                    message.append("Author\n");
                } else if (response.getCategories().equals("")) {
                    message.append("Categories\n");
                } else if (response.getPages() == 0) {
                    message.append("Page count\n");
                } else if (response.getPublisher().equals("")) {
                    message.append("Publisher\n");
                } else if (response.getPubDate().equals("")) {
                    message.append("Publication date");
                }

                if (message.toString().equals("")) {
                    JOptionPane.showMessageDialog(gui, "Record complete. Verify entries before adding book.");
                } else if (message.toString().equals("ISBN\nTitle\nAuthor\nCategories\nPage count\nPublisher\nPublication date")) {
                    JOptionPane.showMessageDialog(gui, "Record not found. Use manual entry or try alternate ISBN.");
                } else {
                    JOptionPane.showMessageDialog(gui, "Incomplete record. Use manual entry for missing fields:\n" + message.toString());
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }


    private String readKey(AddBookGUI gui) {
        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            return reader.readLine();
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(gui, "API key file not found.");
            System.exit(-1);
        }
        return null;
    }
}
}

