package com.aitestgen.controller;

import com.aitestgen.model.ApiInput;
import com.aitestgen.model.ResponseDTO;
import com.aitestgen.model.TestCaseOutput;
import com.aitestgen.service.TestGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class TestCaseController {

    private final TestGeneratorService generatorService;

    public TestCaseController(TestGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping("/generate-tests")
    public ResponseEntity<ResponseDTO> generateTests(@RequestBody ApiInput input) {
        TestCaseOutput output = generatorService.generateTests(input);
        ResponseDTO dto = new ResponseDTO(true, output, null);
        return ResponseEntity.ok(dto);
    }
}
