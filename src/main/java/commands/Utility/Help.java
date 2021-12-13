package commands.Utility;

import commands.base.HybridCommand;
import commands.base.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import utils.Prefix;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static handlers.Commands.COMMANDS;

public class Help extends HybridCommand {

    public String getName() {
        return "help";
    }

    public String getDescription() {
        return "Returns all commands, or one specific command info";
    }

    public String getCategory() {
        return "\u2728 Utility";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription());
    }

    private final String fullHelpDescription = "<:discord:885340297733746798> [Invite Princess Lovely](https://discord.com/api/oauth2/authorize?client_id=907161843221536799&permissions=0&scope=bot%20applications.commands)\n<:kanna:885340978834198608> [Kanna's Kawaii Klubhouse](https://discord.gg/NcPeGuNEdc)\n<:blurplegithub:885340297683406878> [Source Code](https://github.com/MiraBellierr/princess-lovely)\n\n";

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        if (args.toArray().length > 0) {
            getCommandHelp(event, args.get(0).toLowerCase());
        }
        else {
            commandList(event);
        }
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setTitle(String.format("%s Help Command", event.getJDA().getSelfUser().getName()));

        var builder = new StringBuilder();

        COMMANDS.forEach((category, commands) -> {
            builder.append("**").append(category).append("**").append('\n');
            var commandNames = Arrays.stream(commands).map(it -> '`' + it.getName() + '`');
            builder.append(commandNames.collect(Collectors.joining(" ")));
            builder.append('\n');
        });

        embed.setDescription(fullHelpDescription + String.join("\n", builder.toString()));
        event.replyEmbeds(embed.build()).queue();
    }

    private void commandList(@NotNull MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setThumbnail(event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(String.format("Type \"%shelp [command]\" for more info on a command!", new Prefix().getPrefix()), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setTitle(event.getJDA().getSelfUser().getName() + " Help Command");

        var builder = new StringBuilder();

        COMMANDS.forEach((category, commands) -> {
            builder.append("**").append(category).append("**").append('\n');
            var commandNames = Arrays.stream(commands).map(it -> '`' + it.getName() + '`');
            builder.append(commandNames.collect(Collectors.joining(" ")));
            builder.append('\n');
        });

        embed.setDescription(fullHelpDescription + builder.toString());
        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    private void getCommandHelp(@NotNull MessageReceivedEvent event, String input) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(205, 28, 108))
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()));

        Optional<ICommand> optionalCommand = COMMANDS.values().stream().flatMap(Arrays::stream).filter(it -> it.getName().toLowerCase().equals(input)).findFirst();

        if (optionalCommand.isPresent()) {
            var builder = new StringBuilder();
            var command = optionalCommand.get();
            builder.append("**Command name**: ").append(command.getName().toLowerCase()).append('\n');
            builder.append("**Description**: ").append(command.getDescription());
            embed.setDescription(builder.toString());
        } else {
            embed.setDescription(String.format("No information found for command **%s**", input));
        }

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }
}
