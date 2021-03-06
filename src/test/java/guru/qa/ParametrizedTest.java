package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTest {

    @BeforeAll
    static void setUp(){
        Configuration.holdBrowserOpen = true;
    }

    @Disabled
    @ValueSource(strings = {"Fallout", "Postal"})
    @ParameterizedTest(name = "Поиск на сайте GOG игр серии {0}")
    void gogSearchTest(String testData){
        Selenide.open("https://www.gog.com/ru");
        $("svg.menu-icon-svg--search").click();
        $(".menu-search-input__field").setValue(testData);
        $("div.menu-search__results-rows").$("div.menu-search__result").click();
        $(".age-gate__button").click();
        $("h1.productcard-basics__title").shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {"Fallout | Fallout: New Vegas Ultimate Edition", "Postal | POSTAL 4: No Regerts"}, delimiter = '|')
    @ParameterizedTest(name = "Поиск на сайте GOG игр серии {0}, проверка, что первая в списке {1}")
    void gogSearchTestWithVersionCheck(String testData, String checkedData){
        Selenide.open("https://www.gog.com/ru");
        $("svg.menu-icon-svg--search").click();
        $(".menu-search-input__field").setValue(testData);
        $("div.menu-search__results-rows").$("div.menu-search__result").click();
        $(".age-gate__button").click();
        $("h1.productcard-basics__title").shouldHave(Condition.text(checkedData));
    }

    static Stream<Arguments> testGOGWithSource() {
        return Stream.of(
                Arguments.of("Baldur's Gate", "Baldur's Gate"),
                Arguments.of("The Elder Scrolls", "The Elder Scrolls IV: Oblivion")
        );
    }

    @MethodSource("testGOGWithSource")
    @ParameterizedTest(name = "Поиск на сайте GOG игр серии {0}, проверка, что первая в списке {1}")
    void testGOGWithSource(String gameName, String gameVersion) {
        Selenide.open("https://www.gog.com/ru");
        $("svg.menu-icon-svg--search").click();
        $(".menu-search-input__field").setValue(gameName);
        $("div.menu-search__results-rows").$("div.menu-search__result").click();
        $("h1.productcard-basics__title").shouldHave(Condition.text(gameVersion));
    }
}
