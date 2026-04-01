from Symbol import *
from Sentence import *
from Connectives import *

def room2():
    print("Room 2")
    JS = Symbol("Pete and Jill are both liars")
    JT = Symbol("Jill is a truth teller")
    JL = Symbol("Jill is a liar")
    PT = Symbol("Pete is a truth teller")
    PL = Symbol("Pete is a liar")
    
    constraints = And (
        Or(JL, JT),
        Not(And(JL,JT)),
        Or(PT,PL),
        Not(And(PT,PL))
    )
    
    knowledgebase = And(
        constraints,
        Implication(JT, And(JL,PL)),
        Implication(JL, Not(And(JL,PL)))
    )
    
    syms = [JS, JT, JL, PT, PL]
    
    for symbol in syms:
        if model_check(knowledgebase, symbol):
            print(symbol, ": ", model_check(knowledgebase, symbol))
            
    print(syms[0], ": ", model_check(knowledgebase,syms[0]))

def room3():
    print("Room 3")
    TQ = "Sam and Tibo are the same kind"
    SQ = "Tibo and Sam are not the same kind"
    TT = Symbol("Tibo truthteller")
    TL = Symbol("Tibo liar")
    ST = Symbol("Sam truthteller")
    SL = Symbol("Sam liar")

    same_kind = Or(And(TT, ST), And(TL, SL))
    different_kind = Or(And(TT, SL), And(TL, ST))
    
    constraints = And(
        Or(TT,TL),
        Not(And(TT,TL)),
        Or(ST,SL),
        Not(And(ST,SL))
    )
    
    knowledgebase = And(
        constraints,
        Implication(TT, same_kind), # t and s are the same
        Implication(TL, Not(same_kind)), # t and s are not the same
        Implication(ST, different_kind), # t and s are different
        Implication(SL, Not(different_kind)), # t and s are not different
    )
    
    print(TQ, " ", model_check(knowledgebase, same_kind))
    print(SQ, " ", model_check(knowledgebase, different_kind))
    print("Tibo is a truth teller: ", model_check(knowledgebase, TT))
    print("Sam is a truth teller: ", model_check(knowledgebase, ST))

def room4():
    JT = Symbol("Jack is a truth teller")
    JL = Symbol("Jack is a liar")
    AT = Symbol("Ada is a truth teller")
    AL = Symbol("Ada is a liar")
    LT = Symbol("Luke is a truth teller")
    LL = Symbol("Luke is a liar")

    # Room 4 statements
    jack_statement = Or(JT, JL)
    ada_statement = And(JL, LL)
    luke_statement = And(JT, AL)
    
    constraints = And(
        Not(And(JT,JL)),
        Or(JT,JL),
        Not(And(AT, AL)),
        Or(AT,AL),
        Not(And(LT, LL)),
        Or(LT,LL)
    )
    
    knowledgebase = And(
        constraints,
        Implication(JT, jack_statement),
        Implication(JL, Not(jack_statement)),
        Implication(AT, ada_statement),
        Implication(AL, Not(ada_statement)),
        Implication(LT, luke_statement),
        Implication(LL, Not(luke_statement)),
    )
    
    print("Room 4")
    print("Jack is a truth teller:", model_check(knowledgebase, JT))
    print("Jack is a liar:", model_check(knowledgebase, JL))
    print("Ada is a truth teller:", model_check(knowledgebase, AT))
    print("Ada is a liar:", model_check(knowledgebase, AL))
    print("Luke is a truth teller:", model_check(knowledgebase, LT))
    print("Luke is a liar:", model_check(knowledgebase, LL))
    print()
    
    

def main():
    
    room2()
    print()
    room3()
    print()
    room4()


if __name__ == "__main__":
    main()
