package net.leloubil.clonecordserver.data;

public enum Permissions {
    ADMINISTRATOR(1),
    WRITE_MESSAGES(2),
    READ_MESSAGES(4),
    CHANNELS(8);

    int data;

    Permissions(int i) {
        data = i;
    }
}
