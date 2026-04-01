from Sentence import *


class Not(Sentence):
    """Represents a logical NOT operation."""

    def __init__(self, operand):
        """Initialize NOT with an operand."""
        Sentence.validate(operand)
        self.operand = operand

    def __eq__(self, other):
        """Check equality with another NOT operation."""
        return isinstance(other, Not) and self.operand == other.operand

    def __hash__(self):
        """Return hash of the NOT operation."""
        return hash(("not", hash(self.operand)))

    def __repr__(self):
        """Return string representation of NOT."""
        return f"Not({self.operand})"

    def evaluate(self, model):
        """Evaluate NOT operation given a model."""
        return not self.operand.evaluate(model)

    def formula(self):
        """Return the formula representation of NOT."""
        return "~" + Sentence.parenthesize(self.operand.formula())

    def symbols(self):
        """Return symbols in this NOT operation."""
        return self.operand.symbols()


class And(Sentence):
    """Represents a logical AND operation."""

    def __init__(self, *conjuncts):
        """Initialize AND with conjuncts."""
        for conjunct in conjuncts:
            Sentence.validate(conjunct)
        self.conjuncts = list(conjuncts)

    def __eq__(self, other):
        """Check equality with another AND operation."""
        return isinstance(other, And) and self.conjuncts == other.conjuncts

    def __hash__(self):
        """Return hash of the AND operation."""
        return hash(
            ("and", tuple(hash(conjunct) for conjunct in self.conjuncts))
        )

    def __repr__(self):
        """Return string representation of AND."""
        conjuncts = ", ".join([str(conjunct) for conjunct in self.conjuncts])
        return f"And({conjuncts})"

    def add(self, conjunct):
        """Add a conjunct to the AND operation."""
        Sentence.validate(conjunct)
        self.conjuncts.append(conjunct)

    def evaluate(self, model):
        """Evaluate AND operation given a model."""
        return all(conjunct.evaluate(model) for conjunct in self.conjuncts)

    def formula(self):
        """Return the formula representation of AND."""
        if len(self.conjuncts) == 1:
            return self.conjuncts[0].formula()
        return " & ".join(
            [Sentence.parenthesize(conjunct.formula())
             for conjunct in self.conjuncts]
        )

    def symbols(self):
        """Return symbols in this AND operation."""
        return set.union(*[conjunct.symbols() for conjunct in self.conjuncts])


class Or(Sentence):
    """Represents a logical OR operation."""

    def __init__(self, *disjuncts):
        """Initialize OR with disjuncts."""
        for disjunct in disjuncts:
            Sentence.validate(disjunct)
        self.disjuncts = list(disjuncts)

    def __eq__(self, other):
        """Check equality with another OR operation."""
        return isinstance(other, Or) and self.disjuncts == other.disjuncts

    def __hash__(self):
        """Return hash of the OR operation."""
        return hash(
            ("or", tuple(hash(disjunct) for disjunct in self.disjuncts))
        )

    def __repr__(self):
        """Return string representation of OR."""
        disjuncts = ", ".join([str(disjunct) for disjunct in self.disjuncts])
        return f"Or({disjuncts})"

    def evaluate(self, model):
        """Evaluate OR operation given a model."""
        return any(disjunct.evaluate(model) for disjunct in self.disjuncts)

    def formula(self):
        """Return the formula representation of OR."""
        if len(self.disjuncts) == 1:
            return self.disjuncts[0].formula()
        return " | ".join(
            [Sentence.parenthesize(disjunct.formula())
             for disjunct in self.disjuncts]
        )

    def symbols(self):
        """Return symbols in this OR operation."""
        return set.union(*[disjunct.symbols() for disjunct in self.disjuncts])


class Implication(Sentence):
    """Represents a logical implication (if-then)."""

    def __init__(self, antecedent, consequent):
        """Initialize implication with antecedent and consequent."""
        Sentence.validate(antecedent)
        Sentence.validate(consequent)
        self.antecedent = antecedent
        self.consequent = consequent

    def __eq__(self, other):
        """Check equality with another implication."""
        return (isinstance(other, Implication)
                and self.antecedent == other.antecedent
                and self.consequent == other.consequent)

    def __hash__(self):
        """Return hash of the implication."""
        return hash(("implies", hash(self.antecedent), hash(self.consequent)))

    def __repr__(self):
        """Return string representation of implication."""
        return f"Implication({self.antecedent}, {self.consequent})"

    def evaluate(self, model):
        """Evaluate implication given a model."""
        return ((not self.antecedent.evaluate(model))
                or self.consequent.evaluate(model))

    def formula(self):
        """Return the formula representation of implication."""
        antecedent = Sentence.parenthesize(self.antecedent.formula())
        consequent = Sentence.parenthesize(self.consequent.formula())
        return f"{antecedent} => {consequent}"

    def symbols(self):
        """Return symbols in this implication."""
        return set.union(self.antecedent.symbols(), self.consequent.symbols())


class Biconditional(Sentence):
    """Represents a logical biconditional (if and only if)."""

    def __init__(self, left, right):
        """Initialize biconditional with left and right operands."""
        Sentence.validate(left)
        Sentence.validate(right)
        self.left = left
        self.right = right

    def __eq__(self, other):
        """Check equality with another biconditional."""
        return (isinstance(other, Biconditional)
                and self.left == other.left
                and self.right == other.right)

    def __hash__(self):
        """Return hash of the biconditional."""
        return hash(("biconditional", hash(self.left), hash(self.right)))

    def __repr__(self):
        """Return string representation of biconditional."""
        return f"Biconditional({self.left}, {self.right})"

    def evaluate(self, model):
        """Evaluate biconditional given a model."""
        return ((self.left.evaluate(model)
                 and self.right.evaluate(model))
                or (not self.left.evaluate(model)
                    and not self.right.evaluate(model)))

    def formula(self):
        """Return the formula representation of biconditional."""
        left = Sentence.parenthesize(self.left.formula())
        right = Sentence.parenthesize(self.right.formula())
        return f"{left} <=> {right}"

    def symbols(self):
        """Return symbols in this biconditional."""
        return set.union(self.left.symbols(), self.right.symbols())


def model_check(knowledge, query):
    """Check if knowledge base entails query."""
    def check_all(knowledge, query, symbols, model):
        """Check if knowledge base entails query, given a particular model."""
        # Terminal state: no more symbols in the symbol list
        if not symbols:
            # If knowledge base is true in model, then query must also be true
            if knowledge.evaluate(model):
                return query.evaluate(model)
            return True

        # Recursive case: pop one symbol and try both truth values
        remaining = symbols.copy()
        p = remaining.pop()

        # Create model where symbol is true
        model_true = model.copy()
        model_true[p] = True

        # Create model where symbol is false
        model_false = model.copy()
        model_false[p] = False

        # Ensure entailment holds in both models
        return (check_all(knowledge, query, remaining, model_true) and
                check_all(knowledge, query, remaining, model_false))

    # Get all symbols in both knowledge and query
    symbols = set.union(knowledge.symbols(), query.symbols())

    # Check that knowledge entails query
    return check_all(knowledge, query, symbols, dict())
