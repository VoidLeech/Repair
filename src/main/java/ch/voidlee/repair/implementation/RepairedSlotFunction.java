package ch.voidlee.repair.implementation;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;

@FunctionalInterface
public interface RepairedSlotFunction<T> {
    T apply(SlottedStackStorage storage, int slot);
}
