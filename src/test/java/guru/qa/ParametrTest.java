package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ParametrTest {

    @BeforeAll
    static void setUp(){
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://ru.wikipedia.org/";
    }

    @CsvSource(value = {"Физика | область естествознания: наука о наиболее общих законах природы, о материи, её структуре, движении и правилах трансформации.",
                        "Биология | наука о живых существах и их взаимодействии со средой обитания.",
                        "История | процесс развития общества, также наука, исследующая прошлое "},
    delimiter = '|' )
    @ParameterizedTest(name = "Тест {0}, ищем \"{1}\"")
    void wikiTest(String tabName, String projectName) {
        Selenide.open("/wiki/Заглавная_страница");
        $("#searchInput").setValue(tabName);
        $("#searchButton").click();
        $("#bodyContent").shouldHave(text(projectName));
    }

}
