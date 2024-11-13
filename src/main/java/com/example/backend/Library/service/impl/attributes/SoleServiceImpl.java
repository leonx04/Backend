package com.example.backend.Library.service.impl.attributes;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.repository.attributes.MaterialRepository;
import com.example.backend.Library.repository.attributes.SoleRepository;
import com.example.backend.Library.service.interfaces.attributes.MaterialService;
import com.example.backend.Library.service.interfaces.attributes.SoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class SoleServiceImpl implements SoleService {
  SoleRepository soleRepository;
}
