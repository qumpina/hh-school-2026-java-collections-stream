package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны - personConverter.convert()
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 {

  private final PersonConverter personConverter;

  public Task4(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons) {
    List <ApiPersonDto> convertedPersons = persons.stream()
        .map(person -> personConverter.convert(person)) // Идея здесь рекомендует писать personConverter::convert, есть ли в этом смысл?
        .collect(Collectors.toCollection(ArrayList::new));
    return convertedPersons;

  }
}
