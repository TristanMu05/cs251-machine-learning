import sklearn
import csv
from sklearn.neighbors import KNeighborsClassifier
from sklearn.linear_model import Perceptron
from sklearn import svm
from sklearn.naive_bayes import GaussianNB
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split

dataset = []
with open("banknotes.csv", "r") as f:
    reader = csv.reader(f)
    next(reader)
    
    for each_row in reader:
        dataset.append({
            "observations": [float(cell) for cell in each_row[:4]],
            "label": "Authentic" if each_row[4]=="0" else "Counterfeit"
        })
        
observations = [row["observations"] for row in dataset]
labels = [row["label"] for row in dataset]

x_train, x_test, y_train, y_test = train_test_split(observations, labels, test_size=0.3)


model1 = KNeighborsClassifier()
model2 = Perceptron()
#model3 = svm()
model4 = GaussianNB()
model5 = LogisticRegression() 
models = [model1, model2, model4, model5]
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
            
            
    print("For ", model, " we got ", correct, " correct and ", incorrect, " incorrect")
    correct = correct / total
    incorrect = incorrect / total
    print("For ", model, " we got ", correct, "% correct and ", incorrect, "% incorrect")


        