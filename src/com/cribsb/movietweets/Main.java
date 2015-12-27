package com.cribsb.movietweets;
import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chris on 26-12-2015.
 */
public class Main extends JFrame{

    JSONObject search;
    HttpURLConnection connection;

    JLabel[] tweetLabels = new JLabel[100];
    JPanel tweetPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(tweetPanel);
    GridLayout layout = new GridLayout(0,1);


    public Main() {
        search = new JSONObject(getJSON());
        //String status = search.getJSONArray("statuses").getJSONObject(0).getString("text");
        //System.out.println(status);

        tweetPanel.setLayout(layout);

        String status;
        String user;

        int len = search.getJSONArray("statuses").length();
        for(int i = 0; i < len; i++) {
            try {
                status = search.getJSONArray("statuses").getJSONObject(i).getString("text");
                user = search.getJSONArray("statuses").getJSONObject(i).getString("screen_name");
                if (status != null) {
                    tweetLabels[i] = new JLabel("<html><body style='width: 550px;'><h3>" + user + " tweeted: </h3><br>" + status + "</body></html>");
                    tweetPanel.add(tweetLabels[i]);
                }
            } catch(org.json.JSONException e) {
                System.out.println(e.getStackTrace());
            }
        }

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
    }

    public String getJSON() {
        try {
            connection = (HttpURLConnection) new URL("http://loklak.org/api/search.json?timezoneOffset=-60&q=star+wars&count=100").openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            StringBuffer response = new StringBuffer();

            while((input = in.readLine()) != null){
                response.append(input);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args){
        Main app = new Main();
        app.setContentPane(app.scrollPane);
        app.setSize(1600, 900);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
