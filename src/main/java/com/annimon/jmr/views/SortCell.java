package com.annimon.jmr.views;

import com.annimon.jmr.models.Sort;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.DataFormat;
import javafx.util.StringConverter;

public final class SortCell extends CheckBoxListCell<Sort> {

    private static final DataFormat DATA_FORMAT = new DataFormat(Sort.class.getName());

    public SortCell() {
        init();
    }

    private void init() {
        ListCellUtils.setArrangable(this, DATA_FORMAT);

        setConverter(new StringConverter<Sort>() {
            @Override
            public String toString(Sort sort) {
                return sort.getName();
            }

            @Override
            public Sort fromString(String string) {
                return null;
            }
        });

        setSelectedStateCallback(Sort::enabledProperty);
    }
}
