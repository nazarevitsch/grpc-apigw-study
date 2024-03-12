package com.study.grpc.api.gw.controller;

import com.study.grpc.api.gw.dto.PersonView;
import com.study.grpc.api.gw.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<PersonView> create(@RequestBody PersonView person) {
        return ResponseEntity.ok(this.personService.create(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonView> get(@PathVariable Long id) {
        return ResponseEntity.ok(this.personService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonView>> getAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(this.personService.getAll(ids));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.personService.delete(id);
    }

    @GetMapping("/stream")
    public ResponseEntity<List<PersonView>> getStream() {
        return ResponseEntity.ok(this.personService.getAllStream());
    }
}
