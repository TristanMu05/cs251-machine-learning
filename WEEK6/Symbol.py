from Sentence import *


class Symbol(Sentence):
    """Represents a symbol in a logical sentence."""

    def __init__(self, name):
        """Initialize a symbol with a given name."""
        self.name = name

    def __eq__(self, other):
        """Check equality with another symbol."""
        return isinstance(other, Symbol) and self.name == other.name

    def __hash__(self):
        """Return hash of the symbol."""
        return hash(("symbol", self.name))

    def __repr__(self):
        """Return string representation of the symbol."""
        return self.name

    def evaluate(self, model):
        """Evaluate the symbol given a model."""
        return bool(model[self.name])

    def formula(self):
        """Return the formula representation of the symbol."""
        return self.name

    def symbols(self):
        """Return set containing this symbol."""
        return {self.name}
