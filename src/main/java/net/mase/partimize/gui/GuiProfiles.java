package net.mase.partimize.gui;

import net.mase.partimize.ConfigManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiProfiles extends GuiScreen {
    @Override
    public void initGui() {
        buttonList.clear();

        int x = width / 2 - 100;
        int y = 40;
        int step = 30;

        buttonList.add(new GuiButton(0, x, y, 200, 20, "Low (макс FPS)"));
        buttonList.add(new GuiButton(1, x, y + step, 200, 20, "Mid (сбалансированный)"));
        buttonList.add(new GuiButton(2, x, y + step * 2, 200, 20, "High (качество)"));
        buttonList.add(new GuiButton(3, x, y + step * 3, 200, 20, "Ultra (макс качество)"));

        buttonList.add(new GuiButton(10, x, y + step * 5, 200, 20, "Назад"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Выбери профиль", width / 2, 10, 0xFFFFFF);
        drawCenteredString(fontRenderer, "Текущий: " + ConfigManager.currentProfile, width / 2, 25, 0xAAAAAA);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: ConfigManager.applyProfile("low"); mc.displayGuiScreen(new PartimizeGui()); break;
            case 1: ConfigManager.applyProfile("mid"); mc.displayGuiScreen(new PartimizeGui()); break;
            case 2: ConfigManager.applyProfile("high"); mc.displayGuiScreen(new PartimizeGui()); break;
            case 3: ConfigManager.applyProfile("ultra"); mc.displayGuiScreen(new PartimizeGui()); break;
            case 10: mc.displayGuiScreen(new PartimizeGui()); break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}