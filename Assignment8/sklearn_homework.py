"""
EXPECTED OUTPUT SHOULD BE SIMILAR TO THIS:
(py311) root@TristansLaptop:/mnt/c/Users/murad/revival_project/cs251-machine-learning/assignment8# python sklearn_homework.py
KNeighborsClassifier() :  0.8751013787510138 % correct and  0.12489862124898621 % incorrect
KNeighborsClassifier() :  219  true positives,  3018  true negatives,  109  false positives,  353  false negatives
KNeighborsClassifier() :  0.38286713286713286  sensitivity,  0.9651423089222897  specificity
Perceptron() :  0.8629359286293593 % correct and  0.13706407137064072 % incorrect
Perceptron() :  252  true positives,  2940  true negatives,  187  false positives,  320  false negatives
Perceptron() :  0.4405594405594406  sensitivity,  0.9401982731052126  specificity
GaussianNB() :  0.8391457150581239 % correct and  0.16085428494187617 % incorrect
GaussianNB() :  288  true positives,  2816  true negatives,  311  false positives,  284  false negatives
GaussianNB() :  0.5034965034965035  sensitivity,  0.9005436520626798  specificity
LogisticRegression(class_weight='balanced', max_iter=100000, solver='saga') :  0.8110300081103001 % correct and  0.18896999188969993 % incorrect
LogisticRegression(class_weight='balanced', max_iter=100000, solver='saga') :  472  true positives,  2528  true negatives,  599  false positives,  100  false negatives
LogisticRegression(class_weight='balanced', max_iter=100000, solver='saga') :  0.8251748251748252  sensitivity,  0.8084425967380876  specificity
(py311) root@TristansLaptop:/mnt/c/Users/murad/revival_project/cs251-machine-learning/assignment8# 

Overall the Logistic Regression model performed the best in terms of sensitivity and specificity, while the K-Nearest Neighbors model had the highest overall accuracy. The Perceptron and Gaussian Naive Bayes models had lower performance compared to the other two models.
"""




import sklearn
import csv
from sklearn.neighbors import KNeighborsClassifier
from sklearn.linear_model import Perceptron
from sklearn import svm
from sklearn.naive_bayes import GaussianNB
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split

from sklearn.preprocessing import StandardScaler


# PART 1
month_map = {'Jan':0,'Feb':1,'Mar':2,'Apr':3,'May':4,'June':5,'Jul':6,'Aug':7,'Sep':8,'Oct':9,'Nov':10,'Dec':11}
truth_map = {'TRUE':1, 'Returning_Visitor':1}

observations =[]
labels = []

rows = [line.strip().split(',') for line in open('shopping.csv')][1:]

for row in rows:
    observations.append([
        int(row[0]), float(row[1]),
        int(row[2]), float(row[3]),
        int(row[4]), float(row[5]),
        float(row[6]), float(row[7]),
        float(row[8]), float(row[9]),
        int(month_map[row[10]]),
        int(row[11]), int(row[12]),
        int(row[13]), int(row[14]),
        int(truth_map.get(row[15], 0)),
        int(truth_map.get(row[16], 0)),
    ])

    labels.append(truth_map.get(row[17],0))
    
    
x_train, x_test, y_train, y_test = train_test_split(observations, labels, test_size=0.2, stratify=labels)

scaler = StandardScaler()
x_train_scaled = scaler.fit_transform(x_train)
x_test_scaled = scaler.transform(x_test)

model1 = KNeighborsClassifier(n_neighbors=5)
model2 = Perceptron()
#model3 = svm()
model4 = GaussianNB()
model5 = LogisticRegression(max_iter=100000, solver='saga', class_weight='balanced') 

scale_models = [model1, model2]
models = [model4, model5]


for model in scale_models:
    model.fit(x_train_scaled, y_train)

    predictions = model.predict(x_test_scaled)

    correct = 0
    incorrect = 0
    total = 0
    for actual, predicted in zip(y_test, predictions):
        total += 1
        if actual == predicted:
            correct += 1
        else:
            incorrect += 1
            
    tp = 0
    tn = 0
    fp = 0
    fn = 0
    
    for actual, predicted in zip(y_test, predictions):
        if actual == 1 and predicted == 1:
            tp += 1
        elif actual == 0 and predicted == 0:
            tn += 1
        elif actual == 0 and predicted == 1:
            fp += 1
        elif actual == 1 and predicted == 0:
            fn += 1
            
            
    #print(model, ": ", correct, " correct and ", incorrect, " incorrect")
    correct = correct / total
    incorrect = incorrect / total
    print(model, ": ", correct, "% correct and ", incorrect, "% incorrect")
    print(model, ": ", tp, " true positives, ", tn, " true negatives, ", fp, " false positives, ", fn, " false negatives")
    sensitivity = tp / (tp + fn) if (tp + fn) > 0 else 0
    specificity = tn / (tn + fp) if (tn + fp) > 0 else 0
    print(model, ": ", sensitivity, " sensitivity, ", specificity, " specificity")


for model in models:
    model.fit(x_train, y_train)

    predictions = model.predict(x_test)

    correct = 0
    incorrect = 0
    total = 0
    for actual, predicted in zip(y_test, predictions):
        total += 1
        if actual == predicted:
            correct += 1
        else:
            incorrect += 1
            
    tp = 0
    tn = 0
    fp = 0
    fn = 0
    
    for actual, predicted in zip(y_test, predictions):
        if actual == 1 and predicted == 1:
            tp += 1
        elif actual == 0 and predicted == 0:
            tn += 1
        elif actual == 0 and predicted == 1:
            fp += 1
        elif actual == 1 and predicted == 0:
            fn += 1
            
            
    #print(model, ": ", correct, " correct and ", incorrect, " incorrect")
    correct = correct / total
    incorrect = incorrect / total
    print(model, ": ", correct, "% correct and ", incorrect, "% incorrect")
    print(model, ": ", tp, " true positives, ", tn, " true negatives, ", fp, " false positives, ", fn, " false negatives")
    sensitivity = tp / (tp + fn) if (tp + fn) > 0 else 0
    specificity = tn / (tn + fp) if (tn + fp) > 0 else 0
    print(model, ": ", sensitivity, " sensitivity, ", specificity, " specificity")

