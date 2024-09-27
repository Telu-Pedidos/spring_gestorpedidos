package com.api.gestor_pedidos_telu.controller;

import com.api.gestor_pedidos_telu.domain.model.Model;
import com.api.gestor_pedidos_telu.dto.ModelDTO;
import com.api.gestor_pedidos_telu.service.ModelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.gestor_pedidos_telu.infra.security.SecurityConfigurations.SECURITY_NAME;

@RestController()
@RequestMapping("/models")
@Tag(name = "Model")
@SecurityRequirement(name = SECURITY_NAME)
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() {
        List<Model> models = modelService.getAllModels();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable Long id) {
        Model model = modelService.getModelById(id);
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<Model> createModel(@Valid @RequestBody ModelDTO model) {
        Model newModel = modelService.createModel(model);
        return new ResponseEntity<>(newModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @Valid @RequestBody ModelDTO model) {
        Model newModel = modelService.updateModel(id, model);
        return ResponseEntity.ok(newModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Model> deleteModel(@PathVariable Long id) {
        modelService.deleteModel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
