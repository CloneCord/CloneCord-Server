package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.internal.ExternalInstance;

import java.util.Optional;

public interface ExernalInstancesServices {

    ExternalInstance createExternalInstance(ExternalInstance inst);

    Optional<ExternalInstance> findByAddress(String address);

    ExternalInstance updateInstance(ExternalInstance inst);

    void deleteByAddress(String address);

}
