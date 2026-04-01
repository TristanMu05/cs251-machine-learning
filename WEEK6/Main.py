from Sentence import *
from Symbol import *
from Connectives import *


# TASK 1: DEFINE PROPOSITIONAL SYMBOLS
snow = Symbol("snow")
sonya = Symbol("sonya")
natasha = Symbol("natasha")

# TASK 2: DEFINE EACH ASSERTION/SENTENCE AND DISPLAY THE FORMULA
s1 = Implication(Not(snow), sonya)
print("s1: " + s1.formula())

s2 = Or(sonya, natasha)
print("s2: " + s2.formula())

s3 = Not(And(sonya, natasha))
print("s3: " + s3.formula())

s4 = natasha
print("s4: " + s4.formula())

# TASK 3: DEFINE THE KNOWLEDGE-BASE: Add all sentences using And
knowledge= And(s1, s2, s3, s4)


result = model_check(knowledge, snow)
print("\n\nTrue or False: Did it snow? " + str(result))
