package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SimpleCommandListener extends ListenerAdapter {

    public static String[] common_words;

    Toolbox toolbox = new Toolbox();

    public String PREFIX = "!";

    public String[] commands = {"ping", "roll", "prefix", "stop"};

    Random random = new Random();




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
        String[] split_ct = content.split(" ");


        boolean isCommand = content.startsWith(PREFIX);

        if (isCommand) {
            content = content.replace(PREFIX, "");

            if (content.equals("ping")) {
                sendMessage(channel, "Pong!");
            }

            else if (content.equals("roll")) {
                if (split_ct.length >= 3) {
                    sendMessage(channel, String.valueOf(random.nextInt(Integer.parseInt(split_ct[1]),
                            Integer.parseInt(split_ct[2]))));
                }
                else {
                    sendMessage(channel, String.valueOf(random.nextInt(0, 6)));
                }
            }

            else if (content.equals("list")) {
                String ct = Arrays.toString(commands);
                sendMessage(channel, ct);
            }

            else if (content.startsWith("prefix")) {
                String[] ct = content.split(" ");
                if (!(ct.length < 1)) {
                    PREFIX = ct[1];
                    sendMessage(channel, "prefix set to " + PREFIX);
                }
                else sendMessage(channel, "Not enough parameters!");
            }

            else if (content.equals("stop")) {
                if (author.getId().equals("924287870200602645")) System.exit(0);
                else sendMessage(channel, "You do not have the permission to do that. If this appears" +
                        " to be a mistake, please contact <@924287870200602646>.");
            }

            else if (content.equals("song")) {
                sendMessage(channel, toolbox.getSpotifySong());
            }
        }
    }
}
