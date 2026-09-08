package net.mase.partimize;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {
    private static Configuration config;
    private static final Map<String, Boolean> particleDisabled = new ConcurrentHashMap<>();
    public static int renderItemDistance = 45;
    public static int renderPlayersDistance = 45;
    public static int renderParticlesDistance = 45;
    public static int renderGrassDistance = 10;
    public static int guiKey = 25;
    public static String currentProfile = "mid";

    private static final String[] DEFAULT_PARTICLES = {
        "Flame", "Crit", "MagicCrit", "EnchantmentTable", "SweepAttack",
        "Smoke", "HugeExplode", "LargeSmoke", "Heart", "Portal",
        "Bubble", "Splash", "Lava", "Cloud", "DripWater",
        "DripLava", "SnowShovel", "SnowballPoof", "Slime",
        "FireworksSpark", "AngryVillager", "HappyVillager",
        "Spit", "TownAura", "Spell", "InstantSpell",
        "MobSpell", "MobSpellAmbient"
    };

    public static void loadConfig() {
        File configFile = new File("config/partimize.cfg");
        config = new Configuration(configFile);
        config.load();

        for (String name : DEFAULT_PARTICLES) {
            boolean defaultValue = true;
            boolean current = config.getBoolean(name, "PARTICLES", defaultValue, "Отключить частицу " + name);
            particleDisabled.put(name, current);
        }

        renderItemDistance = config.getInt("RenderItem", "DISTANCES", 45, 5, 128, "Дистанция рендера предметов");
        renderPlayersDistance = config.getInt("RenderPlayers", "DISTANCES", 45, 5, 128, "Дистанция рендера игроков");
        renderParticlesDistance = config.getInt("RenderParticles", "DISTANCES", 45, 5, 128, "Дистанция рендера частиц");
        renderGrassDistance = config.getInt("RenderGrass", "DISTANCES", 10, 2, 64, "Дистанция рендера травы");
        guiKey = config.getInt("GuiKey", "DISTANCES", 25, 1, 100, "Клавиша для открытия GUI");

        currentProfile = config.getString("Current", "PROFILES", "mid", "Текущий профиль");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void saveConfig() {
        if (config == null) {
            loadConfig();
            return;
        }

        for (Map.Entry<String, Boolean> entry : particleDisabled.entrySet()) {
            config.get("PARTICLES", entry.getKey(), true).set(entry.getValue());
        }

        config.get("DISTANCES", "RenderItem", 45).set(renderItemDistance);
        config.get("DISTANCES", "RenderPlayers", 45).set(renderPlayersDistance);
        config.get("DISTANCES", "RenderParticles", 45).set(renderParticlesDistance);
        config.get("DISTANCES", "RenderGrass", 10).set(renderGrassDistance);
        config.get("DISTANCES", "GuiKey", 25).set(guiKey);

        config.get("PROFILES", "Current", "mid").set(currentProfile);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static boolean isParticleDisabled(String name) {
        return particleDisabled.getOrDefault(name, true);
    }

    public static void toggleParticle(String name) {
        boolean current = particleDisabled.getOrDefault(name, true);
        particleDisabled.put(name, !current);
        saveConfig();
    }

    public static void applyProfile(String profile) {
        currentProfile = profile.toLowerCase();
        switch (profile.toLowerCase()) {
            case "low":
                particleDisabled.replaceAll((k, v) -> true);
                renderItemDistance = 15; renderPlayersDistance = 15; renderParticlesDistance = 15; renderGrassDistance = 5;
                break;
            case "mid":
                particleDisabled.replaceAll((k, v) -> true);
                particleDisabled.put("Heart", false);
                particleDisabled.put("Crit", false);
                particleDisabled.put("MagicCrit", false);
                renderItemDistance = 30; renderPlayersDistance = 30; renderParticlesDistance = 30; renderGrassDistance = 8;
                break;
            case "high":
                particleDisabled.replaceAll((k, v) -> false);
                renderItemDistance = 45; renderPlayersDistance = 45; renderParticlesDistance = 45; renderGrassDistance = 12;
                break;
            case "ultra":
                particleDisabled.replaceAll((k, v) -> false);
                renderItemDistance = 64; renderPlayersDistance = 64; renderParticlesDistance = 64; renderGrassDistance = 16;
                break;
            default:
                return;
        }
        saveConfig();
    }
}