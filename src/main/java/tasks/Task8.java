package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashSet;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<Resume> resumes = personService.findResumes(new HashSet<>(persons.stream()
        .map(Person::id)
        .collect(Collectors.toSet())));
    Map<Integer, Set<Resume>> mapResumes= new HashMap<>(); // Создаем мапу, где ключ id персоны, а значение - множество ее резюме
    for (Resume resume: resumes){
      if(!mapResumes.containsKey(resume.personId())){
        mapResumes.put(resume.personId(),new HashSet<>(Set.of(resume)));
      }
      else{
        Set<Resume> personResumes=mapResumes.get(resume.personId());
        personResumes.add(resume);
        mapResumes.put(resume.personId(),personResumes);
      }
    }
    Set<PersonWithResumes> enrichedPersonsWithResumes = persons.stream()
        .map(person->new PersonWithResumes(person,mapResumes.getOrDefault(person.id(),Set.of())))
        .collect(Collectors.toSet());
    return enrichedPersonsWithResumes;
  }
}
