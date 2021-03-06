package io.kanteen.service.impl;

import io.kanteen.dto.MealDto;
import io.kanteen.exception.NotFoundException;
import io.kanteen.persistance.entity.Child;
import io.kanteen.persistance.entity.Meal;
import io.kanteen.persistance.repository.IChildRepository;
import io.kanteen.persistance.repository.IMealRepository;
import io.kanteen.service.IMealService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MealService implements IMealService {

    @Autowired
    private IMealRepository mealRepository;
    @Autowired
    private IChildRepository childRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MealDto> getAllMeals() {
        List<Meal> tmp = mealRepository.findAll();
        List<MealDto> result = new ArrayList<>();
        Meal meal = new Meal();
        for (Meal m : tmp){
            result.add(modelMapper.map(m,MealDto.class));
        }
        return result;
    }

    @Override
    public MealDto getMealById(long id) {
        Optional<Meal> tmp = mealRepository.findById(id);
        if (tmp.isPresent()) {
            MealDto meal=modelMapper.map(tmp.get(),MealDto.class);
            return meal;
        } else {
            throw new NotFoundException("Meal not found");
        }
    }

    @Override
    public MealDto saveMeal(MealDto mealDto) {
        Meal meal = modelMapper.map(mealDto,Meal.class);
        mealRepository.save(meal);
        return getMealById(meal.getId());
    }

    @Override
    public List<MealDto> saveMeals(List<MealDto> meals) {
        List<MealDto> result = new ArrayList<>();
        for (MealDto md: meals) {
            result.add(saveMeal(md));
        }
        return result;
    }

    @Override
    public MealDto saveMealNoDto(long idChild, Date day) {
        Optional<Child> tmp = childRepository.findById(idChild);
        if (tmp.isPresent()) {
            Child child = modelMapper.map(tmp.get(),Child.class);
            Meal meal = new Meal();
            meal.setChild(child);
            meal.setDay(day);
            mealRepository.save(meal);
            return getMealById(meal.getId());
        }

        return null;
    }

    @Override
    public void deleteMealById(long id_meal) {
        Optional<Meal> tmp = mealRepository.findById(id_meal);
        if (tmp.isPresent()){
            mealRepository.delete(modelMapper.map(tmp.get(),Meal.class));
        } else {
            throw new NotFoundException("Meal not found and can't be deleted");
        }
    }
}
