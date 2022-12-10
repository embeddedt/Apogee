package com.embeddedt.apogee.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ComponentUtils {
    public static Component concat(Object... args) {
        return Component.translatable("misc.apogee.value_concat", args[0], Component.literal(args[1].toString()).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.GREEN);
    }
}
