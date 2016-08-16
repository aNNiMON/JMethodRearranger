package com.annimon.jmr.models;

import java.util.Comparator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class Sort {

    private final BooleanProperty enabledProperty = new SimpleBooleanProperty(false);

    private final String name;
    private final Comparator<Method> comparator;

    public Sort(String name, Comparator<Method> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public Sort(String name, Comparator<Method> comparator, boolean enabled) {
        this.name = name;
        this.comparator = comparator;
        setEnabled(enabled);
    }

    public BooleanProperty enabledProperty() {
        return enabledProperty;
    }

    public boolean isEnabled() {
        return enabledProperty.get();
    }

    public void setEnabled(boolean enabled) {
        enabledProperty.set(enabled);
    }

    public String getName() {
        return name;
    }

    public Comparator<Method> getComparator() {
        return comparator;
    }
}
