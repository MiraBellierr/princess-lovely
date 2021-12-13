package commands.Fun;

import commands.base.HybridCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Sphere extends HybridCommand {

    public String getName() {
        return "sphere";
    }

    public String getDescription() {
        return "Calculate the volume and surface area of sphere";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription()).addOption(OptionType.INTEGER, "radius", "Provide a radius number", true);
    }

    public void run(@NotNull MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        if (args.toArray().length < 1) {
            event.getMessage().reply("You didn't provide radius!\n\n**NOTE**:\n- This command is to calculate the volume and the surface area of sphere using the radius entered by the user\n- This is my first command I created when I started developing this bot.").mentionRepliedUser(false).queue();
            return;
        }

        double number;

        try {
            number = Double.parseDouble(args.get(0));
        }
        catch(NumberFormatException e) {
            event.getMessage().reply("That doesn't seem to be a valid number!").mentionRepliedUser(false).queue();
            return;
        }

        long radius = java.lang.Math.round(number);
        long volume = java.lang.Math.round(1.333 * 3.142 * radius * radius * radius);
        long diameter = java.lang.Math.round(2 * radius);
        long area = java.lang.Math.round(4 * 3.142 * radius * radius);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("Radius is %d", (int) number))
                .setColor(new Color(205, 28, 108))
                .addField("Diameter:", String.valueOf(diameter), true)
                .addField("Volume Of Sphere:", String.valueOf(volume), true)
                .addField("Surface Area Of Sphere:", String.valueOf(area), true)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        double number = event.getOptions().get(0).getAsDouble();
        long radius = java.lang.Math.round(number);
        long volume = java.lang.Math.round(1.333 * 3.142 * radius * radius * radius);
        long diameter = java.lang.Math.round(2 * radius);
        long area = java.lang.Math.round(4 * 3.142 * radius * radius);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTitle(String.format("Radius is %f", number))
                .setColor(new Color(205, 28, 108))
                .addField("Diameter:", String.valueOf(diameter), true)
                .addField("Volume Of Sphere:", String.valueOf(volume), true)
                .addField("Surface Area Of Sphere:", String.valueOf(area), true)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.replyEmbeds(embed.build()).queue();
    }
}
