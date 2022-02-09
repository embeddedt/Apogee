package com.embeddedt.apogee.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ComponentUtils {
    public static Component concat(Object... args) {
        return new TranslatableComponent("misc.apogee.value_concat", args[0], new TextComponent(args[1].toString()).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GREEN);
    }
}
