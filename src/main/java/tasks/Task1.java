package tasks;

import common.Person;
import common.PersonService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    List<Person> sortedPersons = new ArrayList<>();
    Map<Integer, Person> personsMap = new HashMap<>();
    for (Person p : persons){
        personsMap.put(p.id(),p); // Сложность O(n)
    }
    for (Integer id : personIds){
        sortedPersons.add(personsMap.get(id)); // O(n) для прохода и O(1) для поиска по мапе
    }
    return sortedPersons;
  }
}
