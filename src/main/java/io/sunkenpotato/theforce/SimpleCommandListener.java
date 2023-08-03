package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.Route;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleCommandListener extends ListenerAdapter {

    public static String[] common_words;

    Toolbox toolbox = Main.toolbox;

    public static String PREFIX = "!";

    public String[] commands = {"ping", "roll", "prefix", "stop", "song", "kick"};

    Random random = new Random();
    JDA jda = Main.jda;




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
        Guild guild = event.getGuild();


        boolean isCommand = content.startsWith(PREFIX);

        if (isCommand) {
            content = content.replace(PREFIX, "");

            if (content.equals("ping")) {
                sendMessage(channel, "Pong!");
            }

            else if (content.startsWith("roll")) {
                if (split_ct.length >= 3) {
                    sendMessage(channel, String.valueOf(random.nextInt(Integer.parseInt(split_ct[1]),
                            Integer.parseInt(split_ct[2]))));
                }
                else {
                    sendMessage(channel, String.valueOf(random.nextInt(0, 6)));
                }
            }

            else if (content.equals("list")) {
                sendMessage(channel, Arrays.toString(toolbox.command_descriptions.keySet().toArray()));
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
                if (author.getId().equals("924287870200602646")) System.exit(0);
                else sendMessage(channel, "You do not have the permission to do that. If this appears" +
                        " to be a mistake, please contact <@924287870200602646>.");
            }

            else if (content.equals("song")) {
                if (split_ct.length != 2)
                    sendMessage(channel, toolbox.getSpotifySong());

            }



            else if (content.startsWith("rname")) {
                if (split_ct.length < 2) sendMessage(channel, "Not enough arguments!");
                else {

                    sendMessage(channel, toolbox.genRandomName(Integer.parseInt(split_ct[1])));
                }
            }

            else if (content.startsWith("purge-t")) {
                if (split_ct.length < 2) sendMessage(channel, "Not enough arguments!");
                if (member.hasPermission(Permission.MESSAGE_MANAGE)) {
                    if (toolbox.isParsable(split_ct[1])) {
                        int amount = Integer.parseInt(split_ct[1]);
                        List<Message> messages = channel.getHistory().retrievePast(amount).complete();
                        channel.purgeMessages(messages);
                        sendMessage(channel, "Purged last " + split_ct[1] + "message(s)");

                    } else if (!toolbox.isParsable(split_ct[1]) && split_ct[1].equals("all")) {
                        MessageHistory history = MessageHistory
                                .getHistoryFromBeginning(channel)
                                .complete();
                        channel.purgeMessages(history.getRetrievedHistory());

                        sendMessage(channel, "Deleted all Messages");

                    } else sendMessage(channel, "Unknown argument: "+split_ct[1]);

                }
                else sendMessage(channel, "You don't have the permission to do that!");
            }

        }
    }
}
