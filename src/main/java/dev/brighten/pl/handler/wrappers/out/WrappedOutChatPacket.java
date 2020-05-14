package dev.brighten.pl.handler.wrappers.out;

import dev.brighten.pl.handler.Packet;
import dev.brighten.pl.handler.wrappers.Wrapper;
import dev.brighten.pl.utils.reflection.Reflection;
import dev.brighten.pl.utils.reflection.impl.CraftReflection;
import dev.brighten.pl.utils.reflection.impl.MinecraftReflection;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import dev.brighten.pl.utils.reflection.types.WrappedConstructor;
import dev.brighten.pl.utils.reflection.types.WrappedField;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class WrappedOutChatPacket extends Wrapper {

    private static WrappedClass wrapped = Reflection.getNMSClass(Packet.Server.CHAT);
    private static WrappedConstructor noargsCon = wrapped.getConstructor();
    private static WrappedField fieldComponents = wrapped.getFieldByType(BaseComponent[].class, 0),
            fieldIChatComp = wrapped.getFieldByType(MinecraftReflection.iChatComponent.getParent(), 0);

    public WrappedOutChatPacket() {
        super(noargsCon.newInstance());
    }

    public WrappedOutChatPacket(Object object, Player player) {
        super(object, player);
    }

    public TextComponent component;

    @Override
    protected void onProcess(Object object, Player player) {
        BaseComponent[] components = fieldComponents.get(getObject());

        if(components != null) {
            component = new TextComponent(components);
        } else {
            component = new TextComponent(CraftReflection.getMessageFromComp(fieldIChatComp.get(getObject()),
                    "WHITE"));
        }
    }

    @Override
    public void updateObject() {
        fieldComponents.set(getObject(), TextComponent.fromLegacyText(component.toLegacyText()));
    }


}
