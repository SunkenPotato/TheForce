package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.util.HashMap;
import java.util.Map;

public class SlashCommandListener extends ListenerAdapter {
    Toolbox tb = Main.toolbox;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        String nickname = member.getNickname();
        Role role = event.getGuild().getPublicRole();
        Guild guild = event.getGuild();
        String name = event.getName();

        if (name.equals("echo")) {
            boolean ephemeral = event.getOption("ephemeral").getAsBoolean();
            String content = event.getOption("message").getAsString();
            event.reply(content).setEphemeral(ephemeral).queue();
        }

        else if(name.equals("help")) {
            String command = event.getOption("command").getAsString();
            HashMap<String, Object> c_d = tb.command_descriptions;
            StringBuilder sb = new StringBuilder();

            if (command.equals("all")) {
                for(String key : c_d.keySet()) {
                    sb.append(key);
                    sb.append(" : ");
                    sb.append(c_d.get(key));
                    sb.append("\n");

                }
                event.reply(sb.toString()).setEphemeral(true).queue();
            } else if (c_d.containsKey(command)) {
                sb.append(command);
                sb.append(" : ");
                sb.append(c_d.get(command));
                event.reply(sb.toString()).setEphemeral(true).queue();
                return;
            } else event.reply("No such command: "+command).queue();


        }

        else if (name.equals("prefix")) {
            if (event.getOptions().isEmpty())
                event.reply("The current prefix is: "+ SimpleCommandListener.PREFIX).queue();
            else {
                SimpleCommandListener.PREFIX = event.getOption("prefix").getAsString();
                event.reply("The current prefix is now: "+SimpleCommandListener.PREFIX).queue();
            }
        }
    }
}
