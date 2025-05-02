package pps.gestorClub_api.services.impl;


import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.entities.FeeEntity;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.models.Fee;
import pps.gestorClub_api.repositories.FeeRepository;
import pps.gestorClub_api.services.FeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Fee getById(Long id) {
        FeeEntity feeEntity = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota con id: " + id));

        return modelMapper.map(feeEntity, Fee.class);
    }

    @Override
    public List<Fee> getAll() {

        List<FeeEntity> feeEntities = feeRepository.findAll();

        return feeEntities.stream()
                .map(feeEntity -> {
                    return modelMapper.map(feeEntity, Fee.class);
                }).collect(Collectors.toList());
    }

    @Override
    public Fee create(Fee fee) {

        FeeEntity feeEntity = modelMapper.map(fee, FeeEntity.class);

        return modelMapper.map(feeRepository.save(feeEntity), Fee.class);
    }

    @Override
    public Fee update(Long id, Fee fee) {
        FeeEntity existingEntity = feeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuota con id: " + id));

        FeeEntity updatedEntity = feeRepository.save(modelMapper.map(fee, FeeEntity.class));

        return modelMapper.map(updatedEntity, Fee.class);
    }
}
