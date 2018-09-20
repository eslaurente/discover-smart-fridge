package com.discoverorg.smartfridge.domain;

public enum ItemType {
    APPLE(1),
    ORANGE(2),
    POTATO(3),
    MILK(4);

    private final long typeId;

    ItemType(long typeId) {
        this.typeId = typeId;
    }

    public long getTypeId() {
        return typeId;
    }
}
