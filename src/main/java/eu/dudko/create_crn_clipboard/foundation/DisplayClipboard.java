package eu.dudko.create_crn_clipboard.foundation;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.mrjulsen.crn.block.display.properties.AdvancedDisplaySettingsData;
import de.mrjulsen.mcdragonlib.util.Clipboard;
import eu.dudko.create_crn_clipboard.config.CRNCConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.TagParser;

import java.util.Optional;

public class DisplayClipboard {

    public static Optional<AdvancedDisplaySettingsData> fromClipboard() {
        if (CRNCConfigs.client().useNativeClipboard.get()) {
            AdvancedDisplaySettingsData data = new AdvancedDisplaySettingsData();
            try {
                data.deserializeNbt(TagParser.parseTag(Minecraft.getInstance().keyboardHandler.getClipboard()));
            } catch (CommandSyntaxException e) {
                return Optional.empty();
            }
            return Optional.of(data);
        }
        else {
            return Clipboard.get(AdvancedDisplaySettingsData.class);
        }
    }

    public static void toClipboard(AdvancedDisplaySettingsData data) {
        if (CRNCConfigs.client().useNativeClipboard.get()) {
            Minecraft.getInstance().keyboardHandler.setClipboard((new SnbtPrinterTagVisitor()).visit(data.serializeNbt()));
        }
        else {
            Clipboard.put(AdvancedDisplaySettingsData.class, data);
        }
    }

}
