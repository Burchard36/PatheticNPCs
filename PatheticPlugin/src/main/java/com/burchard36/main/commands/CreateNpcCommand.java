package com.burchard36.main.commands;

import lombok.NonNull;
import org.bukkit.command.*;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.burchard36.main.PatheticPlugin.ofStr;

/**
 * Command format:
 *
 * /createnpc <type> <name> [<SkinUrl | PreferredUuid>] [<PreferredUuid>]
 */
public class CreateNpcCommand implements CommandExecutor, TabCompleter {

    /* Currently, only Player is supported */
    protected static final List<EntityType> supportedEntityTypes
            = new ArrayList<>(Collections.singleton(EntityType.PLAYER));

    @Override
    public boolean onCommand(
                @NonNull CommandSender sender,
                @NonNull Command command,
                @NonNull String label,
                @NonNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console is not allowed to create NPC's!");
            return false;
        }

        if (args.length < 2 || args.length > 4)
            return this.sendInvalidFormat(sender);


        if (args.length == 2) { // simple
            EntityType type = this.get(args);
            if (type == null) return this.sendInvalidEntity(sender);


            return true;
        } else if (args.length == 3) { // with skin url (If you can't parse to URL, try parsing to UUID)

            return true;
        } else { // with skin url + uuid

            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return this.supportedEntitiesForTabCompletions();
        return null;
    }

    protected List<String> supportedEntitiesForTabCompletions() {
        return List.of("PLAYER");
    }

    protected boolean sendInvalidFormat(CommandSender sender) {
        sender.sendMessage(ofStr("&cInvalid command format!"));
        sender.sendMessage(ofStr("&eCorrect format: &a/createnpc &b<type> <name> &d[<SkinUrl | PreferredUuid>] [<PreferredUuid>]"));
        return false;
    }

    protected boolean sendInvalidEntity(CommandSender sender) {
        sender.sendMessage(ofStr("&cInvalid entity provided! Please use a list from: &e&o&nhttps://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html"));
        return false;
    }

    /**
     * Quick util for this class to get the EntityType.
     * @param args Fuck off its protected
     * @return literally fuck off its protected
     */
    protected EntityType get(String[] args) {
        String entityString = args[0];
        try {
            return EntityType.valueOf(entityString);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
