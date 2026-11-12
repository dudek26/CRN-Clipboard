package eu.dudko.create_crn_clipboard.config;

import eu.dudko.create_crn_clipboard.CRNClipboard;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CRNCConfigs {

    protected static CClient client;

    public static CClient client() {
        return client;
    }

    public static void register(ModContainer container) {
        Pair<CClient, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
            CClient config = new CClient();
            config.registerAll(builder);
            return config;
        });

        client = specPair.getLeft();
        client.specification = specPair.getRight();

        container.registerConfig(ModConfig.Type.CLIENT, client.specification);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (client().specification == event.getConfig().getSpec()) client().onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (client().specification == event.getConfig().getSpec()) client().onReload();
    }

    @OnlyIn(Dist.CLIENT)
    public static BaseConfigScreen createConfigScreen(Screen parent) {
        BaseConfigScreen.setDefaultActionFor(CRNClipboard.ID,
                base -> base
                        .withSpecs(client().specification, null, null)
                        .withButtonLabels("Client settings", null, null));
        return new BaseConfigScreen(parent, CRNClipboard.ID);
    }

}
