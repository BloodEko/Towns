package de.bloodeko.towns.core.townchat;

import java.util.Map;

import de.bloodeko.towns.core.townchat.ui.ChatCmd;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;
import de.bloodeko.towns.util.cmds.CmdHandler;

public class ChatFactory {

    /**
     * Loads and registers the town chat UI elements.
     */
    public static void load(Map<String, CmdBase> cmds) {
        CmdHandler handler = new CmdHandler();
        handler.register(Messages.get("cmds.chat.add"), new ChatCmd.AddCmd());
        handler.register(Messages.get("cmds.chat.remove"), new ChatCmd.RemoveCmd());
        handler.register(Messages.get("cmds.chat.switch"), new ChatCmd.SwitchCmd());
        handler.register(Messages.get("cmds.chat.info"), new ChatCmd.InfoCmd());
        handler.register(Messages.get("cmds.chat.leave"), new ChatCmd.LeaveCmd());
        cmds.put(Messages.get("cmds.chat.chat"), new ChatCmd(handler));
    }
}
