import java.util.Map;

public class Main {

    Sentence sentence1 = new Connective(Connective.Type.IMPLIES,
            new Symbol("A"),
            new Symbol("B")
    );

    Map<String, Boolean> model = Map.of("A", true, "B", true);

    Map<String, Boolean> model2 = Map.of("P", true, "Q", true, "R", false);

    Sentence sentence2 = new Connective(Connective.Type.IMPLIES,
        new Connective(Connective.Type.AND,
            new Symbol("P"),
            new Symbol("Q")
            ),
        new Symbol("R")
    );



    public static void main(String[] args) {
        Main main = new Main();
        //System.out.println("Formula: " + main.sentence.formula());
        System.out.println("Formula: " + main.sentence1.formula());
        System.out.println("Model: " + main.model.toString());
        System.out.println("Evaluation: " + main.sentence1.evaluate(main.model) + "\n\n\n");

        System.out.println("Formula: " + main.sentence2.formula());
        System.out.println("Model: " + main.model2.toString());
        System.out.println("Evaluation: " + main.sentence2.evaluate(main.model2));

    }
}
