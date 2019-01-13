package com.onlineshop.controllers;

import com.onlineshop.domain.Nomenclature;
import com.onlineshop.repositorys.NomenclatureRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("nomenclature")
public class NomenclatureController {

    private final NomenclatureRepo nomenclatureRepo;

    public NomenclatureController(NomenclatureRepo nomenclatureRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
    }

    @GetMapping
    public List<Nomenclature> list() {
        return nomenclatureRepo.findAll();
    }

    @PostMapping
    public Nomenclature create(@RequestBody Nomenclature nomenclature) {
        return nomenclatureRepo.save(nomenclature);
    }

    @PutMapping("{id}")
    public Nomenclature update(
            @PathVariable("id") Nomenclature nomenclatureFromDB,
            @RequestBody Nomenclature nomenclature) {

        BeanUtils.copyProperties(
                nomenclature,
                nomenclatureFromDB,
                "id");

        return nomenclatureRepo.save(nomenclatureFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Nomenclature nomenclatureFromDB) {
        nomenclatureRepo.delete(nomenclatureFromDB);
    }
}
