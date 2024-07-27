package com.example.examplemod;

import com.example.examplemod.config.ConfigHolder;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import cn.dancingsnow.dglab.api.ChannelType;
import cn.dancingsnow.dglab.api.Connection;
import cn.dancingsnow.dglab.api.ConnectionManager;
import cn.dancingsnow.dglab.util.DgLabPulseUtil;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "examplemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus modEventBus) {
        ConfigHolder.init();
        // register the event to the bus
        NeoForge.EVENT_BUS.addListener(ExampleMod::onEntityHurt);
    }

    // use forge event to add pulse to player's connection
    public static void onEntityHurt(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            Connection connection = ConnectionManager.getByPlayer(player);
            if (connection != null) {
                float damage = event.getNewDamage();
                int strength;

                if (damage >= 20) {
                    strength = 100;
                } else {
                    strength = Math.round((damage / 20) * 100);
                }

                connection.addPulse(ChannelType.A, DgLabPulseUtil.sinPulse(500, 10, strength, 48));
            }
        }
    }
}
