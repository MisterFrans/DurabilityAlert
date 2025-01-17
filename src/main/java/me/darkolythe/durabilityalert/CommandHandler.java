package me.darkolythe.durabilityalert;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    private DurabilityAlert main = DurabilityAlert.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("durabilityalert.command")) {
                if (cmd.getName().equalsIgnoreCase("durabilityalert")) {
                    if (args.length == 0) {
                        player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert [toggle/armour/tools/type/status/enchant]");
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("toggle")) {
                            main.setPlayerToggle(player, 0);
                            if (main.getPlayerData(player).get(0) == 0) {
                                player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.warningsdisabled);
                            } else {
                                player.sendMessage(main.prefix + ChatColor.GREEN + main.confighandler.warningsenabled);
                            }
                            PlayerSaveManager.save(player);
                        } else if (args[0].equalsIgnoreCase("status")) {
                            Utility.printStatus(player, main);
                        } else if (args[0].equalsIgnoreCase("type")) {
                            player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert type [percent/durability]");
                        } else if (args[0].equalsIgnoreCase("enchant")) {
                            main.setPlayerToggle(player, 4);
                            if (main.getPlayerData(player).get(4) == 0) {
                                player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.enchantedfalse);
                            } else {
                                player.sendMessage(main.prefix + ChatColor.GREEN + main.confighandler.enchantedtrue);
                            }
                            PlayerSaveManager.save(player);
                        } else {
                            player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert [toggle/armour/tools/type/status/enchant]");
                        }
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("type")) {
                            if (args[1].equalsIgnoreCase("durability") || args[1].equalsIgnoreCase("percent")) {
                                main.setPlayerType(player, (args[1].equalsIgnoreCase("durability") ? 1 : 0));
                                player.sendMessage(main.prefix + ChatColor.GREEN + main.confighandler.settype.replaceAll("%type%", args[1]));
                                PlayerSaveManager.save(player);
                            } else {
                                player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert type [percent/durability]");
                            }
                            return true;
                        }
                        try {
                            int percent = Integer.parseInt(args[1]);
                            if (args[0].equalsIgnoreCase("armour") || args[0].equalsIgnoreCase("armor") || args[0].equalsIgnoreCase("a")) {
                                main.setPlayerArmour(player, percent);
                                player.sendMessage(main.prefix + ChatColor.GREEN + main.confighandler.armourset.replaceAll("%armour%", Integer.toString(percent)));
                                PlayerSaveManager.save(player);
                            } else if (args[0].equalsIgnoreCase("tools") || args[0].equalsIgnoreCase("tool") || args[0].equalsIgnoreCase("t")) {
                                main.setPlayerTools(player, percent);
                                player.sendMessage(main.prefix + ChatColor.GREEN + main.confighandler.toolset.replaceAll("%tool%", Integer.toString(percent)));
                                PlayerSaveManager.save(player);
                            } else {
                                player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert [armour/tools]");
                            }
                        } catch (Exception e) {
                            player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.mustbenumber);
                        }
                    } else {
                        player.sendMessage(main.prefix + ChatColor.RED + main.confighandler.invalidarguments + ": /durabilityalert [toggle/armour/tools]");
                    }
                }
            }
        } else {
            sender.sendMessage("Only a player can use that command");
        }

        return true;
    }

}
