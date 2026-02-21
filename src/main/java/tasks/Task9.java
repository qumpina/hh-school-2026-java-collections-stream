package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) { // улучшение читаемости
      return Collections.emptyList();
    }
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList()); // исправлен костыль, теперь стрим самостоятельно скипает первую персону
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // distinct не нужен потому что множество и так хранит только уникальные элементы P.S. стрим избыточен, лучше конструктором
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
        .filter(field -> !field.isEmpty())
        .collect(Collectors.joining(" "));// использование потоков и более короткая запись, наконец впихнул joining
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .collect(Collectors.toMap(Person::id, Person::firstName)); // использование потоков
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    boolean has = false;
    HashSet<Person> personSet1=new HashSet<>(persons1); // тратим память в пользу скорости. Не стал проверять какая из коллекций больше для преобразования в сет, т.к не очень уместно
    for (Person person2 : persons2) {
      if(personSet1.contains(person2)) {
        has=true;
        break;
      }
    }
    return has;
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); // теперь не храним лишнюю переменную count
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString()); // есть 2 предположения: 1 - из-за пула строк. 2 - из-за того,
    // что стандартная хеш функция сортирует их по бакетам так, что они получаются "отсортированными"
  }
}
