package eu.dudko.create_crn_clipboard.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.mrjulsen.crn.block.display.properties.AdvancedDisplaySettingsData;
import de.mrjulsen.crn.block.display.properties.IDisplaySettings;
import de.mrjulsen.crn.block.properties.EDisplayType;
import de.mrjulsen.crn.client.AdvancedDisplaysRegistry;
import de.mrjulsen.crn.client.gui.widgets.FlatIconButton;
import de.mrjulsen.crn.client.gui.windows.AdvancedDisplaySettingsWindow;
import de.mrjulsen.mcdragonlib.client.gui.events.DLGuiStandardEvents;
import de.mrjulsen.mcdragonlib.client.gui.events.DLGuiStandardEvents.ClickEvent;
import de.mrjulsen.mcdragonlib.client.gui.widgets.base.DLGuiComponent;
import de.mrjulsen.mcdragonlib.events.EventListenerId;
import de.mrjulsen.mcdragonlib.events.IEventListener;
import eu.dudko.create_crn_clipboard.foundation.DisplayClipboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancedDisplaySettingsWindow.class)
public abstract class AdvancedDisplaySettingsWindowMixin {

    @Shadow
    private AdvancedDisplaysRegistry.DisplayTypeResourceKey typeKey;

    @Shadow
    private IDisplaySettings settings;

    @Shadow
    private boolean doubleSided;

    @Shadow
    private EDisplayType type;

    @Shadow
    protected abstract void reinit();

    @WrapOperation(method = "init",
            at = @At(value = "INVOKE",
                    target = "Lde/mrjulsen/crn/client/gui/widgets/FlatIconButton;addEventListener(Ljava/lang/Class;Lde/mrjulsen/mcdragonlib/events/IEventListener;)Lde/mrjulsen/mcdragonlib/events/EventListenerId;",
                    ordinal = 0))
    public EventListenerId crn_clipboard$copy(FlatIconButton instance, Class<ClickEvent> clazz, IEventListener<DLGuiComponent, ClickEvent> iEventListener, Operation<EventListenerId> original, @Local(
            name = "copyBtn") FlatIconButton copyBtn) {
        return copyBtn.addEventListener(DLGuiStandardEvents.ClickEvent.class, (s, e) -> {
            DisplayClipboard.toClipboard(new AdvancedDisplaySettingsData(typeKey, settings, doubleSided));
            return false;
        });
    }

    @WrapOperation(method = "init",
            at = @At(value = "INVOKE",
                    target = "Lde/mrjulsen/crn/client/gui/widgets/FlatIconButton;addEventListener(Ljava/lang/Class;Lde/mrjulsen/mcdragonlib/events/IEventListener;)Lde/mrjulsen/mcdragonlib/events/EventListenerId;",
                    ordinal = 1))
    public EventListenerId crn_clipboard$paste(FlatIconButton instance, Class<ClickEvent> clazz, IEventListener<DLGuiComponent, ClickEvent> iEventListener, Operation<EventListenerId> original, @Local(
            name = "pasteBtn") FlatIconButton pasteBtn) {
        return pasteBtn.addEventListener(DLGuiStandardEvents.ClickEvent.class, (s, e) -> {
            DisplayClipboard.fromClipboard().ifPresent(x -> {
                this.typeKey = x.getKey();
                this.type = x.getKey().category();
                this.settings = x.getSettings();
                this.doubleSided = x.isDoubleSided();
                reinit();
            });
            return false;
        });
    }

}
