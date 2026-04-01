from Sentence import *
from Symbol import *
from Connectives import *


# TASK 1: DEFINE PROPOSITIONAL SYMBOLS
M = Symbol("idk")
Q = Symbol("idk")
T = Symbol("idk")

# TASK 2: DEFINE EACH ASSERTION/SENTENCE AND DISPLAY THE FORMULA
s1 = Implication(Not(Q), M)
print("s1: " + s1.formula())

s2 = And(Or(M,T), Not(And(M,Q)))
print("s2: " + s2.formula())

s3 = T
print("s3: " + s3.formula())

# TASK 3: DEFINE THE KNOWLEDGE-BASE: Add all sentences using And
knowledge= And(s1, s2, s3)

# TASK 4: PERFORM MODEL CHECKING TO SEE IF BOBO WILL SWIM.
# Boolean values must be recast as strings. model_check() returns true or false
result = model_check(knowledge, T)
print("\n\nTrue or False: Bobo went for a swim. " + str(result))
