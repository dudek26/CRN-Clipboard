package eu.dudko.create_crn_clipboard;

import eu.dudko.create_crn_clipboard.config.CRNCConfigs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

@Mod(value = CRNClipboard.ID, dist = Dist.CLIENT)
public class CRNClipboardClient {

    private final ModContainer container;

    public CRNClipboardClient(ModContainer container, IEventBus eventBus) {
        this.container = container;
        eventBus.addListener(this::onLoadComplete);
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        Supplier<IConfigScreenFactory>
                configScreen =
                () -> (mc, previousScreen) -> CRNCConfigs.createConfigScreen(previousScreen);
        container.registerExtensionPoint(IConfigScreenFactory.class, configScreen);
    }
}
