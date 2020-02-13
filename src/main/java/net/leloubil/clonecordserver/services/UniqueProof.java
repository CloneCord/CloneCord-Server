package net.leloubil.clonecordserver.services;

public interface UniqueProof<T,S> {

    boolean isUnique(T toCheck, S self);

}
