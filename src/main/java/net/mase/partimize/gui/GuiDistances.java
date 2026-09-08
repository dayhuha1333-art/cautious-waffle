package net.mase.partimize.gui;

import net.mase.partimize.ConfigManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiDistances extends GuiScreen {
    @Override
    public void initGui() {
        buttonList.clear();

        int x = width / 2 - 100;
        int y = 40;
        int step = 25;

        buttonList.add(new GuiButton(0, x, y, 200, 20, "Предметы: " + ConfigManager.renderItemDistance));
        buttonList.add(new GuiButton(1, x, y + step, 200, 20, "Игроки: " + ConfigManager.renderPlayersDistance));
        buttonList.add(new GuiButton(2, x, y + step * 2, 200, 20, "Частицы: " + ConfigManager.renderParticlesDistance));
        buttonList.add(new GuiButton(3, x, y + step * 3, 200, 20, "Трава: " + ConfigManager.renderGrassDistance));

        buttonList.add(new GuiButton(10, x, y + step * 5, 200, 20, "Назад"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Дистанции рендера", width / 2, 10, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                ConfigManager.renderItemDistance = cycleDistance(ConfigManager.renderItemDistance);
                button.displayString = "Предметы: " + ConfigManager.renderItemDistance;
                ConfigManager.saveConfig();
                break;
            case 1:
                ConfigManager.renderPlayersDistance = cycleDistance(ConfigManager.renderPlayersDistance);
                button.displayString = "Игроки: " + ConfigManager.renderPlayersDistance;
                ConfigManager.saveConfig();
                break;
            case 2:
                ConfigManager.renderParticlesDistance = cycleDistance(ConfigManager.renderParticlesDistance);
                button.displayString = "Частицы: " + ConfigManager.renderParticlesDistance;
                ConfigManager.saveConfig();
                break;
            case 3:
                ConfigManager.renderGrassDistance = cycleDistance(ConfigManager.renderGrassDistance);
                button.displayString = "Трава: " + ConfigManager.renderGrassDistance;
                ConfigManager.saveConfig();
                break;
            case 10:
                mc.displayGuiScreen(new PartimizeGui());
                break;
        }
    }

    private int cycleDistance(int current) {
        int[] values = {5, 10, 15, 20, 25, 30, 40, 50, 64, 80, 100, 128};
        for (int i = 0; i < values.length; i++) {
            if (values[i] == current) {
                return values[(i + 1) % values.length];
            }
        }
        return 30;
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}