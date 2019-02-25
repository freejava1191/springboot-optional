package me.freelife.springboottest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTest {
    /**
     * 2. 선택적 개체 만들기
     */
    @Test
    public void whenCreatesEmptyOptinal_thenCorrect() {
        Optional<String> empty = Optional.empty();
        assertThat(empty.isPresent()).isFalse();
    }

    @Test
    public void givenNonNull_whenCreatesOptinal_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.of(name);
        assertThat("Optional[baeldung]").isEqualTo(opt.toString());
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_whenThrowsErrorOnCreate_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.of(name);
    }

    @Test
    public void givenNonNull_whenCreatesNullable_thenCorrect() {
        String name = "baeldung";
        Optional<String> opt = Optional.ofNullable(name);
        assertThat("Optional[baeldung]").isEqualTo(opt.toString());
    }

    @Test
    public void givenNull_whenCreatesNullable_thenCorrect() {
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
        assertThat("Optional.empty").isEqualTo(opt.toString());
    }

    /**
     * 3. 값 존재 확인 :  isPresent() 및 isEmpty() - 11부터 사용가
     */
    @Test
    public void givenAnEmptyOptinal_thenIsEmptyBehavesAsExpected() {
        Optional<String> opt = Optional.of("Baeldung");
        assertThat(opt.isPresent()).isTrue();

        opt = Optional.ofNullable(null);
        assertThat(opt.isPresent()).isFalse();
    }

    /**
     * 4. 조건부 동작 ifPresent()
     */
    /*
    if(name != null){
        System.out.println(name.length);
    }
    */
    @Test
    public void givenOptional_whenIfPresentWorks_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        opt.ifPresent(name -> System.out.println(name.length()));
    }

    /**
     * 5. orElse를 사용한 기본값
     */
    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        assertThat("john").isEqualTo(name);
    }

    /**
     * 6. orElseGet을 사용한 기본값
     */
    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
        assertThat("john").isEqualTo(name);
    }

    /**
     * 7. orElse 와 orElseGet의 차이점
     */
    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }

    @Test
    public void whenOrElseGetAndOrElseOverlap_thenCorrect() {
        String text = null;

        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertThat("Default Value").isEqualTo(defaultText);

        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertThat("Default Value").isEqualTo(defaultText);
    }

    @Test
    public void whenOrElseGetAndOrElseDiffer_thenCorrect() {
        String text = "Text present";

        // 감싸 진 값을 검색하기 위해 orElseGet 을 사용 하면 포함 된 값이 있기 때문에 getMyDefault API가 호출되지 않
        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertThat("Text present").isEqualTo(defaultText);

        // orElse 를 사용 하면 래핑 된 값의 존재 여부에 관계없이 기본 객체가 만들어집니다
        // 따라서이 경우에는 사용되지 않는 하나의 중복 객체를 만들었습니다
        System.out.println("Using orElse:");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertThat("Text present").isEqualTo(defaultText);
    }

    /**
     * 8. orElseThrow의 예외
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenOrElseThrowWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 9. get()을 사용 하여 값 반환
     */
    @Test
    public void givenOptional_whenGetsValue_thenCorrect() {
        Optional<String> opt = Optional.of("baeldung");
        String name = opt.get();
        assertThat("baeldung").isEqualTo(name);
    }

    @Test(expected = NoSuchElementException.class)
    public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
        Optional<String> opt = Optional.ofNullable(null);
        String name = opt.get();
    }

    /**
     * 10. 조건부 반환 filter()
     */
    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2019;
        Optional<Integer> yearOptional = Optional.of(year);
        boolean is2019 = yearOptional.filter(y -> y == 2019).isPresent();
        assertThat(is2019).isTrue();
        boolean is2018 = yearOptional.filter(y -> y == 2018).isPresent();
        assertThat(is2018).isFalse();
    }

    @Data
    public class Modem {
        private Double price;

        public Modem(Double price) {
            this.price = price;
        }
    }

    public boolean priceIsInRange2(Modem modem) {
        boolean isInRange = false;

        if(modem != null && modem.getPrice() != null && (modem.getPrice() >= 10 && modem.getPrice() <= 15)) {
            isInRange = true;
        }
        return isInRange;
    }

    @Test
    public void whenFilterWithoutOptional_thenCorrect() {
        assertThat(priceIsInRange2(new Modem(10.0))).isTrue();
        assertThat(priceIsInRange2(new Modem(9.9))).isFalse();
        assertThat(priceIsInRange2(new Modem(null))).isFalse();
        assertThat(priceIsInRange2(new Modem(15.5))).isFalse();
        assertThat(priceIsInRange2(new Modem(null))).isFalse();
    }

    /**
     * 11. map() 으로 값 변환하기
     */
    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        List<String> companyNames = Arrays.asList("paypal","oracle","","microsoft","","apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional
                .map(List::size)
                .orElse(0);
        assertThat(6).isEqualTo(size);
    }

    @Test
    public void givenOptional_whenMapWorks_thenCorrect2() {
        String name = "baeldung";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional
                .map(String::length)
                .orElse(0);
        assertThat(8).isEqualTo(len);
    }

    @Test
    public void givenOptional_whenMapWorksWithFilter_thenCorrect() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(pass -> pass.equals("password")).isPresent();
        assertThat(correctPassword).isFalse();

        correctPassword = passOpt
                .map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertThat(correctPassword).isTrue();
    }

    /**
     * 12. flatMap() 으로 값 변환하기
     */
    @AllArgsConstructor @NoArgsConstructor
    @Setter
    public class Person {
        private String name;
        private int age;
        private String password;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Integer> getAge() {
            return Optional.ofNullable(age);
        }

        public Optional<String> getPassword() {
            return Optional.ofNullable(password);
        }
    }

    @Test
    public void givenOptional_whenFlatMapWorks_thenCorrect2() {
        Person person = new Person("john", 26);
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper = personOptional.map(Person::getName);
        Optional<String> nameOptional = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertThat("john").isEqualTo(name1);

        String name = personOptional
                .flatMap(Person::getName)
                .orElse("");
        assertThat("john").isEqualTo(name);
    }

    /**
     * 13. JDK 9 Optional API
     *
     * With the release of Java 9 even more new methods were added to the Optional API:
     *
     * the or() method for providing a supplier that creates an alternative Optional
     * the ifPresentOrElse() method that allows executing an action if the Optional is present or another action if not
     * stream() method for converting an Optional to a Stream
     * Here is the complete article for further reading.
     *
     * https://www.baeldung.com/java-9-optional
     */
}
