import java.util.Map;
import java.util.Set;

// Represents logical sentences (with connectives).
// Connective Symbols: not, and, or, implies, bi-conditional.
public abstract class Sentence {
    public abstract boolean evaluate(Map<String, Boolean> model);

    public abstract String formula();

    public abstract Set<String> symbols();

    protected static String parenthesize(String s) {
        if (s == null || s.isEmpty() || s.chars().allMatch(Character::isLetter))
            return s;
        if (s.startsWith("(") && s.endsWith(")") && balanced(s.substring(1,
                s.length() - 1)))
            return s;
        return "(" + s + ")";
    }

    private static boolean balanced(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(')
                count++;
            else if (c == ')') {
                if (count <= 0)
                    return false;
                count--;
            }
        }
        return count == 0;
    }
}
