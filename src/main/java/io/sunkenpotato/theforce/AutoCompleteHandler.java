package io.sunkenpotato.theforce;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompleteHandler extends ListenerAdapter {
    Toolbox tb = Main.toolbox;

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        String name = event.getName();

        if (name.equals("help") && event.getFocusedOption().getName().equals("command")) {
            ArrayList<Command.Choice> options = new ArrayList<>(tb.command_descriptions.keySet().stream()
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                    .map(word -> new Command.Choice(word, word)).toList());
            options.add(new Command.Choice("all", "all"));

            event.replyChoices(options).queue();
        }
    }
}
