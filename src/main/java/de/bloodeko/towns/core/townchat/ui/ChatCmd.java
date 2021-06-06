package de.bloodeko.towns.core.townchat.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townchat.ChatSetting;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;
import de.bloodeko.towns.util.cmds.CmdHandler;

/**
 * Represents the chat command for towns
 * which dispatches to its sub-operations.
 */
public class ChatCmd extends CmdBase {
    private final CmdHandler handler;
    
    public ChatCmd(CmdHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            throw new ModifyException("cmds.chat.syntax");
        }
        handler.onCommand(player, args);
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        return handler.onTabComplete(args, player);
    }
    
    
    /**
     * Acts as a base for chat commands, to provide
     * various functionalities and methods.
     */
    private static abstract class ChatCmdBase extends CmdBase {
        
        /**
         * Returns the selected chat for that UUID.
         * Throws an exception if no chat is selected.
         */
        Integer getChat(UUID uuid) {
            Integer id = Services.townchat().getSelected(uuid);
            if (id == null) {
                throw new ModifyException("chats.town.noChatSelected");
            }
            return id;
        }
        
        /**
         * Returns the town if the player is governor.
         * Throws an exception otherwise.
         */
        Town getTownAccess(UUID uuid, Integer id) {
            Town town = Services.townservice().get(id);
            if (!town.getPeople().isGovernor(uuid)) {
                throw new ModifyException("cmds.base.noEditPermission");
            }
            return town;
        }
        
        /**
         * Throws an exception if the town has no chat.
         */
        void checkChatExists(Town town) {
            checkHasBought(town.getSettings(), ChatSetting.VALUE);
        }
        
        /**
         * Throws an exception if the player is not in the specified chat.
         */
        void checkPlayerIsInChat(UUID uuid, Integer id) {
            Set<Integer> chats = Services.townchat().getChats(uuid);
            if (!chats.contains(id)) {
                throw new ModifyException("cmds.chat.youAreNotInside");
            }
        }
    }
    
    
    /**
     * Allows governors to add players a town chat.
     * Denies if the player is already inside the chat.
     */
    public static class AddCmd extends ChatCmdBase {

        @Override
        public void execute(Player player, String[] args) {
            Integer id = getChat(player.getUniqueId());
            Town town = getTownAccess(player.getUniqueId(), id);
            String name = getArg(0, args);
            Player target = getTarget(name);
            checkChatExists(town);
            
            if (Services.townchat().getPlayers(id).contains(target.getUniqueId())) {
                throw new ModifyException("cmds.chat.isAlreadyMember", target.getName());
            }
            
            Services.townchat().addPlayer(town.getId(), target.getUniqueId());
            Messages.say(player, "cmds.chat.addedPlayer", target.getName(), town.getName());
        }
    }
    
    /**
     * Allows governors to remove members from a town chat.
     * Denies for non-players and other governors.
     */
    public static class RemoveCmd extends ChatCmdBase {

        @Override
        public void execute(Player player, String[] args) {
            Integer id = getChat(player.getUniqueId());
            Town town = getTownAccess(player.getUniqueId(), id);
            String name = getArg(0, args);
            OfflinePlayer target = getOfflineTarget(name);
            checkChatExists(town);
            
            if (!Services.townchat().getPlayers(town.getId()).contains(target.getUniqueId())) {
                throw new ModifyException("cmds.chat.isNoMember", target.getName());
            }
            if (Services.peopleservice().getPeople(town.getId()).isGovernor(target.getUniqueId())) {
                throw new ModifyException("cmds.chat.cannotRemoveGovernor");
            }
            
            Services.townchat().removePlayer(town.getId(), target.getUniqueId());
            Messages.say(player, "cmds.chat.removedPlayer", target.getName(), town.getName());
        }
    }
    
    /**
     * Allows players to switch to another chat.
     */
    public static class SwitchCmd extends ChatCmdBase {
        
        @Override 
        public void execute(Player player, String[] args) {
            String name = getArg(0, args);
            Integer id = Services.nameservice().getId(name);
            checkPlayerIsInChat(player.getUniqueId(), id);
            
            Services.townchat().switchChat(player.getUniqueId(), id);
            Messages.say(player, "cmds.chat.switched", Services.nameservice().getName(id));
        }
        
        @Override
        public List<String> completeTab(String[] args, Player player) {
            List<String> names = Services.townchat().getChatNames(player.getUniqueId());
            if (args.length == 1) {
                return Util.filterList(names, args[0]);
            }
            return null;
        }
    }
    
    /**
     * Allows players to leave a chat, if they are 
     * added either automatically or manually.
     */
    public static class LeaveCmd extends ChatCmdBase {

        @Override
        public void execute(Player player, String[] args) {
            Integer id = getChat(player.getUniqueId());
            checkPlayerIsInChat(player.getUniqueId(), id);
            
            Services.townchat().removePlayer(id, player.getUniqueId());
            Messages.say(player, "cmds.chat.left", Services.nameservice().getName(id));
        }
    }
    
    /**
     * Allows players to see all online players in the selected chat.
     * Use the "full" argument, to see both online/offline players.
     */
    public static class InfoCmd extends ChatCmdBase {
        private final String fullCmd = Messages.get("cmds.chat.info.fullCmd");

        @Override
        public void execute(Player player, String[] args) {
            Integer id = getChat(player.getUniqueId());
            Messages.say(player, "cmds.chat.info.header", Services.nameservice().getName(id));
            
            if (args.length == 0) {
                Messages.say(player, "cmds.chat.info.players", getOnlineNames(id));
            } else {
                Messages.say(player, "cmds.chat.info.allPlayers", getAllNames(id));
            }
        }
        
        @Override
        public List<String> completeTab(String[] args) {
            if (args.length == 1) {
                return Util.filterList(Arrays.asList(fullCmd), args[0]);
            }
            return Arrays.asList("");
        }
        
        /**
         * Returns all online players formatted.
         */
        private String getOnlineNames(Integer id) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Player player : Services.townchat().getOnlinePlayers(id)) {
                joiner.add(player.getName());
            }
            return joiner.toString();
        }
        
        /**
         * Returns all online/offline players formatted.
         */
        private String getAllNames(Integer id) {
            StringJoiner joiner = new StringJoiner(", ");
            for (UUID uuid : Services.townchat().getPlayers(id)) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                joiner.add(player.getName());
            }
            return joiner.toString();
        }
    }
}
