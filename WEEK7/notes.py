'''
Probablility
 * For example a die has a 1/6 chance on landing on each face.
 * if we have 2 dice we can caculate the probability of each outcome by multiplying the probabilities of each die.
 * For example the probability of rolling a 1 and a 2 is 1/6 * 1/6 = 1/36
 * We can calculate the probability we roll a sum of 7 by counting the number of outcomes that result in a 7 and dividing by the total number of outcomes. There are 6 outcomes that result in a 7 (1,6), (2,5), (3,4), (4,3), (5,2), (6,1) so the probability is 6/36 = 1/6
 
 
Probablility Distributions
 * A probability distribution is a function that assigns probabilities to each possible outcome of a random variable.
 * For example the probability distribution of rolling a die is:
    * P(1) = 1/6
    * P(2) = 1/6
    * P(3) = 1/6
    * P(4) = 1/6
    * P(5) = 1/6
    * P(6) = 1/6

Unconditional Probability
 * The probability of an event occurring without any conditions or restrictions.
 * For example the probability of rolling a 3 on a die is 1/6.
 * If we roll a 3 7 times in a row, whats the probability of rolling a 3 on the next roll?
 * Because its unconditional, its still 1/6 prob.
 
 
Random Variables
 * a numeric mapping of the sample space (the set of all possible outcomes) in a random event.
 * For example, if we flip a coin twice, what are the set of all possible outcomes:
 * HH, HT, TH, TT
 * We can define a random variable X that maps these outcomes to the number of heads:
 * X(HH) = 2
 * X(HT) = 1
 * X(TH) = 1 
 * X(TT) = 0
 * The probability distribution of X is:
    * P(X=0) = 1/4
    * P(X=1) = 1/2
    * P(X=2) = 1/4
    
 Bayes' Theorem
  * Allows us to update prbobabilities based on new evidence.
    * P(A|B) = P(B|A) * P(A) / P(B)
    
 * Weather scenario:
 weather is collected over a period of time
 1) 10% of the days had rainy afternoons
 2) prob of clouds 40%
 3) prob of clouds given rain is 80%
 * What is the prob of rain given clouds?
 
 * Steps for solving
 1) define events critical to the problem
 2) determine the probabilities of these events
 3) apply Bayes' theorem to find the desired probability
 
 P(rain | clouds) = P(clouds | rain) * P(rain) / P(clouds)
 P(rain | clouds) = 0.8 * 0.1 / 0.4 = 0.2
 
 Joint Probability
 * somtimes 2 events are not independent and occur at the same time
 
 
 
 AI and Bayesian Networks
 * uses a directed graph with indvidual nodes representing random variables and edges representing dependencies between them.
 * A->B means A has a direct influence on B
 * B depends upon A
 
'''