package commands.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import utils.Prefix;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Roll {

    public String getName() {
        return "roll";
    }

    public String getDescription() {
        return "Rolls a specified number of dice with a specified number of sides.";
    }

    public String getCategory() {
        return "\uD83E\uDDE9 Fun";
    }

    public CommandData slashCommand() {
        return new CommandData(this.getName(), this.getDescription())
                .addOption(OptionType.INTEGER, "dice", "Number of dice", true)
                .addOption(OptionType.INTEGER, "sides", "number of sides per die", true);
    }

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        if (args.toArray().length < 2) {
            event.getMessage().reply(String.format("Please roll in an accepted format\n`%sroll <# of dice> <# of sides per die>`", new Prefix().getPrefix())).mentionRepliedUser(false).queue();
            return;
        }

        try {
            Integer.parseInt(args.get(0));
            Integer.parseInt(args.get(1));
        }
        catch(NumberFormatException e) {
            event.getMessage().reply(String.format("Please roll in an accepted format\n`%sroll <# of dice> <# of sides per die>`", new Prefix().getPrefix())).mentionRepliedUser(false).queue();
            return;
        }

        int dice = Integer.parseInt(args.get(0));
        int sides = Integer.parseInt(args.get(1));

        if (dice > 15 || sides > 120) {
            event.getMessage().reply("Please provide a valid amount of dice and sides. (no more than 15 die and/or 120 sides)").mentionRepliedUser(false).queue();
            return;
        }
        else if (dice < 1 || sides < 1) {
            event.getMessage().reply("Can't roll non-existent die with/or non-existent sides").mentionRepliedUser(false).queue();
            return;
        }

        String result = roll(dice, sides);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("You rolled: %s", result));

        event.getMessage().replyEmbeds(embed.build()).mentionRepliedUser(false).queue();
    }

    public void runSlashCommand(@NotNull SlashCommandEvent event) {
        int dice = (int) event.getOptions().get(0).getAsLong();
        int sides = (int) event.getOptions().get(1).getAsLong();

        if (dice > 15 || sides > 120) {
            event.reply("Please provide a valid amount of dice and sides. (no more than 15 die and/or 120 sides)").queue();
            return;
        }
        else if (dice < 1 || sides < 1) {
            event.reply("Can't roll non-existent die with/or non-existent sides").queue();
            return;
        }

        String result = roll(dice, sides);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(new Color(205, 28, 108))
                .setDescription(String.format("You rolled: %s", result));

        event.replyEmbeds(embed.build()).queue();
    }

    private @NotNull String roll(int dice, int sides) {
        ArrayList<String> results = new ArrayList<>();

        for (int i = 0; i < dice; i++) {
            results.add(String.valueOf((int) java.lang.Math.floor(java.lang.Math.random() * sides) + 1));
        }

        String resDel = String.join(", ", results);

        return resDel.replaceAll("/,(?=[^,]*$)/", " and");
    }
}
