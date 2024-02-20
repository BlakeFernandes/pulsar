package com.github.blakefernandes.pulsar.commands;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public abstract class Command {
    private TerminableConsumer terminableConsumer;
    public abstract void execute(CommandSender sender, ImmutableList<String> args);
}
