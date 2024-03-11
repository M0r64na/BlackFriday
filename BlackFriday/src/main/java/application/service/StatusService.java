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
//    private static final IStatusRepository statusRepository = new StatusRepository();

    private final IStatusRepository statusRepository;

    @Inject
    public StatusService(IStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status findByName(OrderStatus orderStatus) {
        return statusRepository.findByOrderStatus(orderStatus);
    }

    @Override
    @PostConstruct
    public void initialize() {
        if(!statusRepository.getAll().isEmpty()) return;

        statusRepository.create(new Status(OrderStatus.IN_PROCESS));
        statusRepository.create(new Status(OrderStatus.SHIPPING));
        statusRepository.create(new Status(OrderStatus.DELIVERED));
    }
}