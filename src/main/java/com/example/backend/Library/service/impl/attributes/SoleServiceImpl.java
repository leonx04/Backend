package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.repository.attributes.MaterialRepository;
import com.example.backend.Library.repository.attributes.SoleRepository;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
import com.example.backend.Library.service.interfaces.attributes.SoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoleServiceImpl implements SoleService {
    private final SoleRepository soleRepo;

    //Cần xử lý ex cho add , update , changeStatus

    @Transactional
    @Override
    public Sole create(Sole sole) {
        soleRepo.save(sole);
        return sole;
    }

    @Override
    public Optional<Sole> findById(Integer id) {
        return soleRepo.findById(id);//optional<Sole> // empty Optional
    }

    @Override
    public List<Sole> findAll() {
        List<Sole> brands = soleRepo.findAll();
        return (brands.isEmpty() ? Collections.emptyList() : brands);
    }

    @Transactional
    @Override
    public Sole update(Sole sole) {
        if (!soleRepo.existsById(sole.getId())) {
            throw new ResourceNotFoundException("Update failed: Sole not found with ID: " + sole.getId());
        }
        return soleRepo.save(sole);
    }

    @Override
    public boolean existsById(Integer integer) {
        return soleRepo.existsById(integer);
    }

    @Override
    public void changeStatus(int id, int status) {
        Sole sole = soleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sole update status with id: " + id + " failed!"));
        sole.setStatus(status);
        soleRepo.save(sole);
    }

}
