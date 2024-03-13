package application.service;

import application.service.interfaces.IStatusService;
import data.model.entity.Status;
import data.model.entity.enums.OrderStatus;
import data.repository.StatusRepository;
import data.repository.interfaces.IStatusRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

public class StatusService implements IStatusService {
    private final IStatusRepository statusRepository;

    @Inject
    public StatusService(IStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status findStatusByName(OrderStatus orderStatus) {
        return this.statusRepository.findByOrderStatus(orderStatus);
    }

    @Override
    @PostConstruct
    public void initializeStatuses() {
        if(!this.statusRepository.getAll().isEmpty()) return;

        this.statusRepository.create(new Status(OrderStatus.IN_PROCESS));
        this.statusRepository.create(new Status(OrderStatus.SHIPPING));
        this.statusRepository.create(new Status(OrderStatus.DELIVERED));
    }
}