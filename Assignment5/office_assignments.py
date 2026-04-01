"""
CS251 Knowledge Engineering — Assignment 5
Departmental Office Assignments using Model Checking
"""

from itertools import product

# ─── Domain ───────────────────────────────────────────────────────────────────
PROFESSORS = ['Adams', 'Baker', 'Clark', 'Baca']
OFFICES    = ['A', 'B', 'C', 'D']

# Atomic propositions: every (professor, office) pair is a boolean variable.
# model[(p, o)] = True means professor p is assigned to office o.
PROPOSITIONS = [(p, o) for p in PROFESSORS for o in OFFICES]


# ─── Knowledge Base ───────────────────────────────────────────────────────────

def fact1_each_professor_assigned(model):
    """Fact 1: Every professor is assigned to at least one office."""
    return all(any(model[(p, o)] for o in OFFICES) for p in PROFESSORS)

def fact2_exactly_one_office(model):
    """Fact 2: Each professor occupies exactly one office (not more than one)."""
    return all(sum(model[(p, o)] for o in OFFICES) == 1 for p in PROFESSORS)

def fact3_one_professor_per_office(model):
    """Fact 3: Each office is occupied by at most one professor."""
    return all(sum(model[(p, o)] for p in PROFESSORS) <= 1 for o in OFFICES)

def fact4_adams_a_or_b(model):
    """Fact 4: Dr. Adams will be placed in Office A or Office B."""
    return model[('Adams', 'A')] or model[('Adams', 'B')]

def fact5_baker_not_c(model):
    """Fact 5: Dr. Baker will NOT be placed in Office C."""
    return not model[('Baker', 'C')]

def fact6_clark_in_a(model):
    """Fact 6: Dr. Clark will definitely occupy Office A."""
    return model[('Clark', 'A')]

KNOWLEDGE_BASE = [
    fact1_each_professor_assigned,
    fact2_exactly_one_office,
    fact3_one_professor_per_office,
    fact4_adams_a_or_b,
    fact5_baker_not_c,
    fact6_clark_in_a,
]


# ─── Model Checking ───────────────────────────────────────────────────────────

def entails(model):
    """Return True if the model satisfies every sentence in the knowledge base."""
    return all(constraint(model) for constraint in KNOWLEDGE_BASE)

def model_check():
    """
    Enumerate all 2^16 possible truth assignments over the 16 propositions.
    Return every model that satisfies the entire knowledge base.
    """
    valid  = []
    total  = 0
    for values in product((False, True), repeat=len(PROPOSITIONS)):
        total += 1
        model = dict(zip(PROPOSITIONS, values))
        if entails(model):
            valid.append(model)
    return valid, total


# ─── Display ──────────────────────────────────────────────────────────────────

def print_assignment(model):
    for p in PROFESSORS:
        office = next(o for o in OFFICES if model[(p, o)])
        print(f"  Dr. {p:<8} -> Office {office}")

def main():
    print("=" * 50)
    print("  CS251 Knowledge Engineering — Assignment 5")
    print("  Departmental Office Assignments")
    print("=" * 50)

    print("\nKnowledge Base:")
    for i, fact in enumerate(KNOWLEDGE_BASE, 1):
        print(f"  [{i}] {fact.__doc__}")

    print("\nRunning model checking over all possible worlds...\n")
    valid_models, total_checked = model_check()

    print(f"  Possible worlds checked : {total_checked:,}")
    print(f"  Worlds satisfying KB    : {len(valid_models)}")
    print()

    if not valid_models:
        print("No valid assignment exists — the knowledge base is inconsistent.")
    else:
        for i, model in enumerate(valid_models, 1):
            print(f"Valid Assignment #{i}:")
            print_assignment(model)
            print()

if __name__ == '__main__':
    main()
