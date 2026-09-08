package net.mase.partimize.gui;

import net.mase.partimize.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class PartimizeGui extends GuiScreen {
    private int fps = 0;
    private long lastFpsUpdate = 0;

    @Override
    public void initGui() {
        buttonList.clear();

        int x = width / 2 - 100;
        int y = 30;
        int step = 25;

        buttonList.add(new GuiButton(0, x, y, 200, 20, "Частицы (вкл/выкл)"));
        buttonList.add(new GuiButton(1, x, y + step, 200, 20, "Дистанции"));
        buttonList.add(new GuiButton(2, x, y + step * 2, 200, 20, "Профили"));

        buttonList.add(new GuiButton(10, x, y + step * 4, 90, 20, "Low"));
        buttonList.add(new GuiButton(11, x + 95, y + step * 4, 90, 20, "Mid"));
        buttonList.add(new GuiButton(12, x, y + step * 5, 90, 20, "High"));
        buttonList.add(new GuiButton(13, x + 95, y + step * 5, 90, 20, "Ultra"));

        buttonList.add(new GuiButton(20, x, y + step * 7, 200, 20, "Сохранить и закрыть"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2 - 75, 10, 0);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        drawCenteredString(fontRenderer, "Partimize v1.0", 0, 0, 0xFFFFFF);
        GlStateManager.popMatrix();

        updateFps();
        String fpsText = "FPS: " + fps;
        int color = fps >= 60 ? 0x00FF00 : (fps >= 30 ? 0xFFFF00 : 0xFF0000);
        drawCenteredString(fontRenderer, fpsText, width / 2, 55, color);

        drawCenteredString(fontRenderer, "Текущий профиль: " + ConfigManager.currentProfile, width / 2, 75, 0xAAAAAA);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                for (String key : ConfigManager.particleDisabled.keySet()) {
                    ConfigManager.particleDisabled.put(key, !ConfigManager.particleDisabled.get(key));
                }
                ConfigManager.saveConfig();
                break;
            case 1:
                mc.displayGuiScreen(new GuiDistances());
                break;
            case 2:
                mc.displayGuiScreen(new GuiProfiles());
                break;
            case 10: ConfigManager.applyProfile("low"); break;
            case 11: ConfigManager.applyProfile("mid"); break;
            case 12: ConfigManager.applyProfile("high"); break;
            case 13: ConfigManager.applyProfile("ultra"); break;
            case 20: mc.displayGuiScreen(null); break;
        }
    }

    private void updateFps() {
        long now = System.currentTimeMillis();
        if (now - lastFpsUpdate > 50) {
            fps = Minecraft.getDebugFPS();
            lastFpsUpdate = now;
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}