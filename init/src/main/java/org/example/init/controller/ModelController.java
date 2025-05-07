package org.example.init.controller;

import org.example.init.DTO.ChatRequest;
import org.example.init.DTO.ChatResponse;
import org.example.init.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return modelService.handleChat(request);
    }
}
