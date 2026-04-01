
import java.util.Map;
import java.util.Objects;
import java.util.Set;

//
public class Symbol extends Sentence {
    private String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        return model.get(name);
    }

    @Override
    public String formula() {
        return name;
    }

    @Override
    public Set<String> symbols() {
        return Set.of(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Symbol s) &&
                s.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
