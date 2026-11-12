package eu.dudko.create_crn_clipboard.config;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.createmod.catnip.config.ConfigBase;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CClient extends ConfigBase {

    public final ConfigGroup client = group(0, "client", Comments.client);

    public final ConfigBool useNativeClipboard = b(true, "useNativeClipboard", Comments.useNativeClipboard);

    @Override
    public String getName() {
        return "client";
    }


    private static class Comments {
        static String client = "Client-only settings";
        static String
                useNativeClipboard =
                "If active, copied display configurations will be placed in the computer-wide clipboard and when pasting display configurations will be taken from the computer-wide clipboard.";
    }
}
