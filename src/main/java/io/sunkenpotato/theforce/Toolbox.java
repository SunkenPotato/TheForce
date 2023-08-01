package io.sunkenpotato.theforce;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Toolbox {

    HttpClient client = HttpClient.newHttpClient();
    Random random = new Random();
    static String[] common_words;
    String token;
    String client_id, client_secret;
    String access_token;


    public String istream_to_string(InputStream is) {
        StringBuilder sb = new StringBuilder();

        try {
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
        } catch (Exception ignored) {}
        return sb.toString();
    }

    public Toolbox() {
        client_id = System.getProperty("client.id");
        client_secret = System.getProperty("client.secret");

        String temp = istream_to_string(Toolbox.class.getResourceAsStream("/common-words"));

        common_words = temp.split("\n");

        getAccessToken(client_id, client_secret);
    }

    int getAccessToken(String cl_id, String cl_s) {
        try {
            HttpRequest request = HttpRequest.newBuilder().
                    uri(URI.create("https://accounts.spotify.com/api/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers
                            .ofString("grant_type=client_credentials&client_id=" + cl_id
                                    + "&client_secret=" + cl_s)).build();

            HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(resp.body());
            access_token = (String) obj.get("access_token");


            return resp.statusCode();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return 400;
    }

    public String getSpotifySong() {
        String item = common_words[random.nextInt(0, 5000)];

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spotify.com/v1/search?q="+item+"&type=track&market=de" +
                        "&limit=5"))
                .header("Authorization", "Bearer "+access_token).build();

        HttpResponse<String> resp = null;

        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (resp.statusCode() == 401) return "!";

        String response = resp.body();

        JSONObject root = new JSONObject(response);
        JSONObject tracks = root.getJSONObject("tracks");
        JSONArray items = tracks.getJSONArray("items");
        JSONObject track_obj = (JSONObject) items.get(new Random().nextInt(0, items.length() - 1));
        JSONObject ext_urls = track_obj.getJSONObject("external_urls");

        return ext_urls.getString("spotify");
    }
}
