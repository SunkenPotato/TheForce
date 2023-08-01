package io.sunkenpotato.theforce;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main implements EventListener {

    public static String TOKEN;




    public static void main(String[] args) throws InterruptedException{
        TOKEN = System.getProperty("token");
        System.out.println(TOKEN);
        JDA jda = JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new Main())
                .addEventListeners(new SimpleCommandListener())
                .build();

        jda.awaitReady();
    }

    @Override()
    public void onEvent(GenericEvent event){
        if (event instanceof ReadyEvent) {
            System.out.println("bot ready.");
        }
    }
}
