package com.embeddedt.apogee.spawner.rei;

import com.embeddedt.apogee.Apogee;
import com.embeddedt.apogee.spawner.modifiers.SpawnerModifier;
import com.embeddedt.apogee.spawner.modifiers.StatModifier;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.stats.StatFormatter.DECIMAL_FORMAT;

public class SpawnerCategory implements DisplayCategory<SpawnerDisplay> {
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.SPAWNER);
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("title.apogee.spawner");
    }

    @Override
    public CategoryIdentifier<SpawnerDisplay> getCategoryIdentifier() {
        return SpawnerREIClientPlugin.SPAWNER;
    }

    private List<Component> getTooltipForModifier(StatModifier<?> s) {
        List<Component> list = new ArrayList<>();
        list.add(s.stat.name().withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE));
        list.add(s.stat.desc().withStyle(ChatFormatting.GRAY));
        if (s.value instanceof Number) {
            if (((Number) s.min).intValue() > 0 || ((Number) s.max).intValue() != Integer.MAX_VALUE) list.add(new TextComponent(" "));
            if (((Number) s.min).intValue() > 0) list.add(new TranslatableComponent("misc.apogee.min_value", s.min).withStyle(ChatFormatting.GRAY));
            if (((Number) s.max).intValue() != Integer.MAX_VALUE) list.add(new TranslatableComponent("misc.apogee.max_value", s.max).withStyle(ChatFormatting.GRAY));
        }
        return list;
    }

    @Override
    public List<Widget> setupDisplay(SpawnerDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 17);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y - 5)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 15)).entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createLabel(new Point(startPoint.x - 15, startPoint.y - 2), new TextComponent("?")).tooltip(new TranslatableComponent("misc.apogee.mainhand")));
        widgets.add(Widgets.createLabel(new Point(startPoint.x - 15, startPoint.y + 18), new TextComponent("?")).tooltip(new TranslatableComponent("misc.apogee.offhand")));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        StatModifier<?> s = display.getModifier().getStatModifiers().get(0);
        String value = s.value.toString();
        if (value.equals("true")) value = "+";
        else if (value.equals("false")) value = "-";
        else if (s.value instanceof Number num && num.intValue() > 0) value = "+" + value;
        Component msg = new TranslatableComponent("misc.apogee.concat", value, s.stat.name()).withStyle(ChatFormatting.BLACK);
        int width = Minecraft.getInstance().font.width(msg);
        List<Component> tooltipList = getTooltipForModifier(s);
        widgets.add(Widgets.createLabel(new Point(bounds.getCenterX(), startPoint.y + 35), msg).noShadow().tooltip(tooltipList.toArray(new Component[0])));
        return widgets;
    }
}
