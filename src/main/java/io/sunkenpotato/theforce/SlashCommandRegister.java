package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class SlashCommandRegister {
    JDA jda;

    public SlashCommandRegister(JDA jda) {
        this.jda = jda;

        registerEchoCommand();

    }

    void registerEchoCommand() {
        // Echo command
        jda.upsertCommand(
                Commands.slash("echo", "Echoes a message back to you")
                        .addOption(OptionType.STRING, "message", "The message to echo back")
                        .addOption(OptionType.BOOLEAN, "ephemeral", "Whether it should not be visible to others")
        ).queue();
        // Help command
        jda.upsertCommand(
                Commands.slash("help", "Gives help on a certain/all command(s)")
                        .addOption(OptionType.STRING, "command", "The command to get help on/'all'", true, true)
        ).queue();
        // Prefix command
        jda.upsertCommand(
                Commands.slash("prefix", "gets/sets the server prefix")
                        .addOption(OptionType.STRING, "prefix", "(optional) prefix to be set")
        ).queue();


    }


    void registerHelpCommand() {


        System.out.println(jda.retrieveCommands().complete());
    }
}
