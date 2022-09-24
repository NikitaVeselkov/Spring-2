package org.example;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    public static final PetModel petModel = PetModel.getInstance();
    public static final AtomicInteger  newId= new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "text/html" )
    public String createPet(@RequestBody Pet pet){
        String result = "";

        if(petModel.getAll().isEmpty() ){
            result = "Поздравляем вы создали первого питомца" + petModel.getAll().size();
        } else {
            result = "Вы создали питомца" + petModel.getAll().size();
        }
        petModel.add(pet,newId.getAndIncrement());
        return result;
    }


    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json",produces = "application/json")
    public Pet getPet(@RequestBody Map<String,Integer> id ){
        return petModel.getFromList(id.get("id")) ;
    }
    @DeleteMapping(value = "/deletePet",consumes = "application/json",produces = "text/html")
    public String deletePet(@RequestBody Map<String,Integer> id ){
        petModel.getAll().remove(id.get("id"));
        return "Запись о питомце удалена";
    }

    @PutMapping(value = "/putPet",produces = "application/json",consumes = "application/json")
    public Pet putPet (@RequestBody Map<String,String> id){

        int idPet = Integer.parseInt(id.get("id"));
        String name = id.get("name");
        String type = id.get("type");
        int age = Integer.parseInt(id.get("age"));

        Pet pet = new Pet(name,type,age);
        petModel.add(pet,idPet);

        return petModel.getFromList(idPet);
    }


}
