package eu.dudko.create_crn_clipboard;

import com.mojang.logging.LogUtils;
import eu.dudko.create_crn_clipboard.config.CRNCConfigs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CRNClipboard.ID)
public class CRNClipboard {
    public static final String ID = "create_crn_clipboard";
    public static final String NAME = "Create: CRN Clipboard";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CRNClipboard(IEventBus eventBus, ModContainer container) {
        CRNCConfigs.register(container);
    }

    public static ResourceLocation id(String key) {
        return ResourceLocation.fromNamespaceAndPath(CRNClipboard.ID, key);
    }

}
