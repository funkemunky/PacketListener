package dev.brighten.pl.handler.wrappers.in;

import dev.brighten.pl.handler.Packet;
import dev.brighten.pl.handler.wrappers.Wrapper;
import dev.brighten.pl.utils.reflection.Reflection;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import dev.brighten.pl.utils.reflection.types.WrappedConstructor;
import dev.brighten.pl.utils.reflection.types.WrappedField;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class WrappedInChatPacket extends Wrapper {

    private static WrappedClass wrappedClass = Reflection.getNMSClass(Packet.Client.CHAT);
    private static WrappedConstructor noArgsConst = wrappedClass.getConstructor();
    private static WrappedField messageField = wrappedClass.getFieldByType(String.class, 0);

    public WrappedInChatPacket() {
        super(noArgsConst.newInstance());
    }

    public WrappedInChatPacket(Object object, Player player) {
        super(object, player);
    }

    private String message;

    @Override
    protected void onProcess(Object object, Player player) {
        message = messageField.get(object);
    }

    @Override
    public void updateObject() {
        messageField.set(getObject(), message);
    }
}
