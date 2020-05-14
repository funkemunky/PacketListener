package dev.brighten.pl.processor;

import dev.brighten.pl.handler.wrappers.Wrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/*
    An asynchronous processor for packets.
 */
public class PacketProcessor {
    private Map<EventPriority, BiFunction<Wrapper, String, Boolean>> processors = new HashMap<>();
    private List<BiConsumer<Wrapper, String>> asyncProcessors = new ArrayList<>();
    private ExecutorService asyncThread = Executors.newSingleThreadExecutor();

    public void process(EventPriority priority, BiFunction<Wrapper, String, Boolean> function) {
        processors.put(priority, function);
    }

    public void process(BiFunction<Wrapper, String, Boolean> function) {
        process(EventPriority.NORMAL, function);
    }

    public void processAsync(BiConsumer<Wrapper, String> consumer) {
        asyncProcessors.add(consumer);
    }

    public boolean call(Wrapper packet, String type) {
        asyncThread.execute(() -> asyncProcessors.forEach(con -> con.accept(packet, type)));
        return processors.keySet().stream().sorted(Comparator.comparing(EventPriority::getSlot))
                .map(processors::get)
                .anyMatch(func -> func.apply(packet, type));
    }

    public void shutdown() {
        processors.clear();
        asyncProcessors.clear();
        asyncThread.shutdown();

        //nullifying everything
        processors = null;
        asyncProcessors = null;
        asyncThread = null;
    }
}
