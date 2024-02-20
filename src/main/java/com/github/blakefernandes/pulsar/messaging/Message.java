package com.github.blakefernandes.pulsar.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lucko.helper.text3.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Message {

    private List<String> message;
    private static final Map<String, List<String>> messageList = new HashMap<>();

    public Message(List<String> message) {
        this.message = message;
    }

    public Message(String message) {
        this.message = List.of(message);
    }

    public static Message of(@NotNull List<String> message) {
        return new Message(message);
    }

    public static Message of(@NotNull String message) {
        return new Message(message);
    }

    public static Message get(String key) {
        return new Message(messageList.getOrDefault(key, List.of(Text.colorize("&cCould not find message " + key))));
    }

    public static Message getOrDefault(String key, Message defaultMessage) {
        return new Message(messageList.getOrDefault(key, defaultMessage.asList()));
    }

    public static void loadMessages(YamlConfiguration configuration) {
        for (String key : configuration.getKeys(false)) {
            if (configuration.isList(key))
                messageList.put(key, configuration.getStringList(key));
            else if (configuration.isString(key))
                messageList.put(key, List.of(configuration.getString(key)));
        }
    }

    public Message colorize() {
        message.forEach(Text::colorize);
        return this;
    }

    public List<String> asList() {
        return message;
    }

    public String firstString() {
        return message.get(0);
    }

    public Message send(CommandSender sender, Placeholder... placeholders){
        transform(placeholders);
        message.forEach(msg -> sender.sendMessage(Text.colorize(msg)));
        return this;
    }

    public Message transform(Placeholder... placeholders){
        message = message.stream().map(msg -> setPlaceholders(msg, placeholders)).collect(Collectors.toList());
        return this;
    }

    public Message transform(PlaceholderList... placeholders) {
        message = message.stream().map(msg -> setPlaceholders(msg, placeholders)).flatMap(List::stream).collect(Collectors.toList());
        return this;
    }

    private List<String> setPlaceholders(String msg, PlaceholderList... placeholders) {
        List<String> output = new ArrayList<>();

        boolean done = false;

        for (PlaceholderList placeholder : placeholders){
            String placeholderText = "%" + placeholder.getName() + "%";
            if (msg.contains(placeholderText)) {
                output.addAll(placeholder.getValue());
                done = true;
            }
        }

        if (!done) output.add(msg);

        return output;
    }

    private String setPlaceholders(String text, Placeholder... placeholders){
        for(Placeholder placeholder : placeholders){
            text = text.replace("%" + placeholder.getName() + "%", placeholder.getValue());
        }

        return text;
    }

    @Getter
    public static class Placeholder {
        private String name;
        private String value;

        public Placeholder(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PlaceholderList {
        private String name;
        private List<String> value;
    }
}
