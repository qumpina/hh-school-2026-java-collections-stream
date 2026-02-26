package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream().sorted(Comparator.comparing(Person::secondName, Comparator.nullsLast(String::compareTo)) // добавил компаратор для обработки null значений
            .thenComparing(Person::firstName, Comparator.nullsLast(String::compareTo))// попробовал в тесте заменить одно из имен на null и поймал java.lang.NullPointerException
            .thenComparing(Person::createdAt)) // избавился от лишней переменной и изменил коллектор на toList
        .toList();
  }
}
