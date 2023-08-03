package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.http.HypixelHttpClient;
import net.hypixel.api.unirest.UnirestHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.UUID;


public class Toolbox {

    HttpClient client = HttpClient.newHttpClient();
    Random random = new Random();
    static String[] common_words;
    String token;
    String client_id, client_secret;
    String access_token;
    HypixelHttpClient hypixelClient;
    HypixelAPI hypixelAPI;
    char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};
    char[] consonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k',
            'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};


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
        client_id = System.getProperty("spotify.id");
        client_secret = System.getProperty("spotify.secret");

        String temp = istream_to_string(Toolbox.class.getResourceAsStream("/common-words"));

        common_words = temp.split("\n");

        getAccessToken(client_id, client_secret);
    }

    void getAccessToken(String cl_id, String cl_s) {
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



        } catch (Exception e) {
            e.printStackTrace();
        }


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

        if (resp.statusCode() == 401) {
            getAccessToken(client_id, client_secret);
            return "Please try again. If this doesn't work, contact sunkenpotato";
        };

        String response = resp.body();

        JSONArray root = new JSONObject(response)
                .getJSONObject("tracks")
                .getJSONArray("items");

        JSONObject track_obj = (JSONObject) root.get(new Random()
                .nextInt(0, root.length() - 1));
        JSONObject ext_urls = track_obj.getJSONObject("external_urls");

        return ext_urls.getString("spotify");
    }



    public String genRandomName(int len) {
        StringBuilder sb = new StringBuilder();


        if (len % 2 == 0) for (int i = 0; i < len / 2; i++) {
            sb.append(consonants[random.nextInt(0, consonants.length - 1)]);
            sb.append(vowels[random.nextInt(0, vowels.length - 1)]);
        }
        else {
            for (int i = 0; i < len / 2; i++) {
                sb.append(consonants[random.nextInt(0, consonants.length - 1)]);
                sb.append(vowels[random.nextInt(0, vowels.length - 1)]);
            }
            sb.append(consonants[random.nextInt(0, consonants.length - 1)]);
        }

        return sb.toString();
    }

    public boolean isParsable(String n) {
        try {
            Integer.parseInt(n);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }
}
