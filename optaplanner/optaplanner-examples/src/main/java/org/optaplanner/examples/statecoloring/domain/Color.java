package org.optaplanner.examples.statecoloring.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.common.swingui.components.Labeled;

public@JsonIdentityInfo(scope = Color.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
class Color extends AbstractPersistable implements Labeled {
    private String name;

    public Color() {
    }

    public Color(long id) {
        super(id);
    }

    public Color(long id, String name) {
        this(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return name;
    }
}
