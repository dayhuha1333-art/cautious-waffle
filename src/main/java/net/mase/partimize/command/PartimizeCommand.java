package net.mase.partimize.command;

import net.mase.partimize.ConfigManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class PartimizeCommand extends CommandBase {
    @Override
    public String getName() {
        return "partimize";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/partimize <reload|profile <low|mid|high|ultra>>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("§6Partimize v1.0 - /partimize reload или /partimize profile <name>"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ConfigManager.loadConfig();
            sender.sendMessage(new TextComponentString("§aКонфиг перезагружен!"));
            return;
        }

        if (args[0].equalsIgnoreCase("profile") && args.length > 1) {
            ConfigManager.applyProfile(args[1]);
            sender.sendMessage(new TextComponentString("§aПрофиль изменен на: " + args[1]));
            return;
        }

        sender.sendMessage(new TextComponentString("§cИспользование: " + getUsage(sender)));
    }
}