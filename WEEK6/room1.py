from Symbol import *
from Sentence import *
from Connectives import *

#from model_check import model_check

def main():
    Query = Symbol("John is a truth teller")
    
    T = Symbol("John is a truth teller")
    L = Symbol("John is a liar")
    
    s1 = And(Or(T,L), Not(And(T,L)))
    
    s2 = Implication(T, Query)
    
    s3 = Implication(L, Not(Query))
    
    s4 = Biconditional(And(T,L), Query)
    
    knowledge = And(s1, s2, s3, s4)
    
    print("Is John telling the truth?" , model_check(knowledge, Query))


if __name__ == "__main__":
    main()