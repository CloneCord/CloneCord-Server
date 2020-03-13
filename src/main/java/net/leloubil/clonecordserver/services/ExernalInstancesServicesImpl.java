package net.leloubil.clonecordserver.services;

import net.leloubil.clonecordserver.data.internal.ExternalInstance;
import net.leloubil.clonecordserver.persistence.ExternalInstancesRepository;

import java.util.Optional;

public class ExernalInstancesServicesImpl implements ExernalInstancesServices {

    private final ExternalInstancesRepository externalInstancesRepository;

    public ExernalInstancesServicesImpl(ExternalInstancesRepository externalInstancesRepository) {
        this.externalInstancesRepository = externalInstancesRepository;
    }

    @Override
    public ExternalInstance createExternalInstance(ExternalInstance inst) {
        return externalInstancesRepository.insert(inst);
    }

    @Override
    public Optional<ExternalInstance> findByAddress(String address) {
        return externalInstancesRepository.findById(address);
    }

    @Override
    public ExternalInstance updateInstance(ExternalInstance inst) {
        return externalInstancesRepository.save(inst);
    }

    @Override
    public void deleteByAddress(String address) {
        externalInstancesRepository.deleteById(address);
    }
}
