package com.api.gestor_pedidos_telu.service;

import com.api.gestor_pedidos_telu.domain.model.Model;
import com.api.gestor_pedidos_telu.dto.ModelDTO;
import com.api.gestor_pedidos_telu.infra.exception.NotFoundException;
import com.api.gestor_pedidos_telu.repository.ModelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Model getModelById(Long id) {
        return findModelByIdOrThrow(id);
    }

    public Model createModel(ModelDTO data) {
        Model newModel = new Model(data);
        validateModelNameUniqueness(newModel.getName(), null);
        return modelRepository.save(newModel);
    }

    public Model updateModel(Long id, ModelDTO data) {
        Model model = findModelByIdOrThrow(id);
        validateModelNameUniqueness(model.getName(), id);

        BeanUtils.copyProperties(data, model);
        return modelRepository.save(model);
    }

    public void deleteModel(Long id) {
        findModelByIdOrThrow(id);
        modelRepository.deleteById(id);
    }

    private void validateModelNameUniqueness(String name, Long modelId) {
        Optional<Model> existingModel = modelRepository.findByName(name);

        if (existingModel.isPresent() && !existingModel.get().getId().equals(modelId)) {
            throw new IllegalArgumentException("Já existe um modelo com esse nome");
        }
    }

    private Model findModelByIdOrThrow(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
    }

}
