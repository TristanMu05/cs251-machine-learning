
// ─────────────────────────────
// CONNECTIVE
// ─────────────────────────────
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Connective extends Sentence {
    public enum Type {
        NOT, AND, OR, IMPLIES, BICONDITIONAL
    }

    private final Type type;
    private final List<Sentence> operands;

    public Connective(Type type, Sentence... operands) {
        this.type = type;
        this.operands = List.of(operands);
        // Validate operand counts for each connective type
        int n = this.operands.size();
        switch (type) {
            case NOT -> {
                if (n != 1) throw new IllegalArgumentException("NOT connective requires exactly 1 operand, got " + n);
            }
            case IMPLIES, BICONDITIONAL -> {
                if (n != 2) throw new IllegalArgumentException(type + " connective requires exactly 2 operands, got " + n);
            }
            case AND, OR -> {
                if (n < 1) throw new IllegalArgumentException(type + " connective requires at least 1 operand, got " + n);
            }
        }
    }

    @Override
    public boolean evaluate(Map<String, Boolean> model) {
        switch (type) {
            case NOT:
                return !operands.get(0).evaluate(model);
            case AND:
                return operands.stream().allMatch(s -> s.evaluate(model));
            case OR:
                return operands.stream().anyMatch(s -> s.evaluate(model));
            case IMPLIES:
                return !operands.get(0).evaluate(model) || operands.get(1).evaluate(model);
            case BICONDITIONAL:
                return operands.get(0).evaluate(model) == operands.get(1).evaluate(model);
            default:
                throw new IllegalStateException("Unexpected type: " + type);
        }
    }

    // NOTE: Typing the Not Connective:
    // PC: Hold down the Alt key and type 170 on the numeric keypad.
    // MAC: OPTION + L
@Override
    public String formula() {
        switch (type) {
            case NOT:
                return "~" + parenthesize(operands.get(0).formula());
            case AND:
                return join(" AND ");
            case OR:
                return join(" OR ");
            case IMPLIES:
                return parenthesize(operands.get(0).formula()) + " -> " +
                        parenthesize(operands.get(1).formula());
            case BICONDITIONAL:
                return parenthesize(operands.get(0).formula()) + " <-> " +
                        parenthesize(operands.get(1).formula());
            default:
                throw new IllegalStateException("Unexpected type: " + type);
        }
    }

    private String join(String delimiter) {
        return operands.stream().map(Sentence::formula).map(Sentence::parenthesize)
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public Set<String> symbols() {
        return operands.stream().flatMap(s -> s.symbols().stream()).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return formula();
    }
}
