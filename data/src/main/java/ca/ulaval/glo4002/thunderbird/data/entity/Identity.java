package ca.ulaval.glo4002.thunderbird.data.entity;

import java.util.UUID;

public abstract class Identity {
    public final String id;

    public Identity() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Identity)) return false;

        Identity identity = (Identity) obj;

        return id.equals(identity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}