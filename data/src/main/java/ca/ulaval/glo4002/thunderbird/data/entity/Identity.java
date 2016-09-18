package ca.ulaval.glo4002.thunderbird.data.entity;

import java.util.UUID;

/*
 * Created by maxime on 2016-09-16.
 */
public abstract class Identity {
    public final String id;

    public Identity() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;

        Identity identity = (Identity) o;

        return id.equals(identity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}