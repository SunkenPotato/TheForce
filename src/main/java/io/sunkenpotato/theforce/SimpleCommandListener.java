package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Random;

public class SimpleCommandListener extends ListenerAdapter {

    public String PREFIX = "!";

    public String[] commands = {"ping", "roll"};

    public void sendMessage(MessageChannel channel, String content) {
        channel.sendMessage(content).queue();
    }




    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        String nickname = member.getNickname();
        Role role = event.getGuild().getPublicRole();


        boolean isCommand = content.startsWith(PREFIX);

        if (isCommand) {
            content = content.replace(PREFIX, "");

            if (content.equals("ping")) {
                sendMessage(channel, "Pong!");
            }

            else if (content.equals("roll")) {
                sendMessage(channel, String.valueOf(new Random().nextInt(0, 6)));
            }

            else if (content.equals("list")) {
                String ct = Arrays.toString(commands);
                sendMessage(channel, ct);
            }

            else if (content.startsWith("prefix")) {
                String[] ct = content.split(" ");
                PREFIX = ct[1];
                sendMessage(channel, "prefix set to " + PREFIX);
            }

            else if (content.equals("stop") && author.getId().equals("924287870200602646")) {
                System.exit(0);
            }
        }
    }
}
