package commands.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Sphere {

    public String getName() {
        return "sphere";
    }

    public String getDescription() {
        return "Calculate the volume and surface area of sphere";
    }

    public String getCategory() {
        return "Fun";
    }

    public void run(MessageReceivedEvent event, @NotNull ArrayList<String> args) {
        if (args.toArray().length < 1) {
            event.getChannel().sendMessage("You didn't provide radius!\n\n**NOTE**:\n- This command is to calculate the volume and the surface area of sphere using the radius entered by the user\n- This is my first command I created when I started developing this bot.").queue();
            return;
        }

        double number;

        try {
            number = Double.parseDouble(args.get(0));
        }
        catch(NumberFormatException e) {
            event.getChannel().sendMessage("That doesn't seem to be a valid number!").queue();
            return;
        }

        long radius = Math.round(number);
        long volume = Math.round(1.333 * 3.142 * radius * radius * radius);
        long diameter = Math.round(2 * radius);
        long area = Math.round(4 * 3.142 * radius * radius);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl())
                .setTitle(String.format("Radius is %f", number))
                .setColor(new Color(205, 28, 108))
                .addField("Diameter:", String.valueOf(diameter), true)
                .addField("Volume Of Sphere:", String.valueOf(volume), true)
                .addField("Surface Area Of Sphere:", String.valueOf(area), true)
                .setTimestamp(Instant.from(ZonedDateTime.now()))
                .setFooter(event.getJDA().getSelfUser().getAsTag(), event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
    }
}
