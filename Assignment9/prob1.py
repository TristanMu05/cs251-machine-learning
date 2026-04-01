import sklearn
import csv
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.linear_model import Perceptron
from sklearn.datasets import load_iris, load_wine, load_breast_cancer
from sklearn.metrics import accuracy_score
import time
from sklearn.preprocessing import StandardScaler

# TASK 1
data = load_iris()

X = data.data 
y = data.target 

# TASK 2
print("Number of samples: ", X.shape[0])
print("Number of features: ", X.shape[1])

unique, counts = np.unique(y, return_counts=True)
print("Class distribution: ", dict(zip(unique, counts)))

# TASK 3
observations = X.tolist()
labels = y.tolist()
    
x_train, x_test, y_train, y_test = train_test_split(observations, labels, test_size=0.2, stratify=labels)

perceptron = Perceptron()
knn = KNeighborsClassifier(n_neighbors=3)

perceptron.fit(x_train, y_train)
knn.fit(x_train, y_train)

perceptron_predictions = perceptron.predict(x_test)

# PART 2
correct_perceptron = accuracy_score(y_test, perceptron_predictions)
tp = 0
tn = 0
fp = 0
fn = 0

for actual, predicted in zip(y_test, perceptron_predictions):
    if actual == 1 and predicted == 1:
        tp += 1
    elif actual == 0 and predicted == 0:
        tn += 1
    elif actual == 0 and predicted == 1:
        fp += 1
    elif actual == 1 and predicted == 0:
        fn += 1
        
"""
Expected output: 
(py311) root@TristansLaptop:/mnt/c/Users/murad/revival_project/cs251-machine-learning/assignment9# python prob1.py
Number of samples:  150
Number of features:  4
Class distribution:  {np.int64(0): np.int64(50), np.int64(1): np.int64(50), np.int64(2): np.int64(50)}
Perceptron:  28  correct out of  30
Sensitivity:  1.0
Specificity:  0.9
KNN:  27  correct out of  30
(py311) root@TristansLaptop:/mnt/c/Users/murad/revival_project/cs251-machine-learning/assignment9# 

Based on the output, the Perceptron model perfoms well on this dataset, achieving a high sensitivity of 1.0 and a specificity of 0.9. Something to note is that the Perceptrons performance will vary a lot 
on the iris dataset, with some runs getting 0 sensitvity. Overall, Perceptron would not be a good choice for this dataset and is very sensitive to hyperparameters.
"""

knn_predictions = knn.predict(x_test)

# PART 3
ktp = 0
ktn = 0
kfp = 0
kfn = 0

for actual, predicted in zip(y_test, knn_predictions):
    if actual == 1 and predicted == 1:
        ktp += 1
    elif actual == 0 and predicted == 0:
        ktn += 1
    elif actual == 0 and predicted == 1:
        kfp += 1
    elif actual == 1 and predicted == 0:
        kfn += 1

correct_knn = accuracy_score(y_test, knn_predictions)

"""
PART 3
Values I tried for k: 3, 5, 7, 17
The model performed best with k=5 and k=7, struggling slightly with k=3, and with a noticiable drop in performance with k=17. This is likely because with a larger k, the model is more likely to misclassify
samples that are close to the decision boundary, as it will be influenced by more distant neighbors. With a smaller k, the model may be more sensitive to noise in the data, which can also lead to 
misclassifications. Overall, k=5 and k=7 seem to strike a good balance between bias and variance for this dataset.
"""


print("Perceptron: ", correct_perceptron * 100, "% correct out of ", len(y_test), "\nSensitivity: ", tp / (tp + fn) if (tp + fn) > 0 else 0, "\nSpecificity: ", tn / (tn + fp) if (tn + fp) > 0 else 0)
print("KNN: ", correct_knn * 100, "% correct out of ", len(y_test), "\nSensitivity: ", ktp / (ktp + kfn) if (ktp + kfn) > 0 else 0, "\nSpecificity: ", ktn / (ktn + kfp) if (ktn + kfp) > 0 else 0)

# PART 4 DIRECT MODEL COMPARISON
start = time.time()
perceptron.fit(x_train, y_train)
perceptron_train_time = time.time() - start

start = time.time()
knn.fit(x_train, y_train)
knn_train_time = time.time() - start

start=time.time()
perceptron_predictions = perceptron.predict(x_test)
perceptron_predict_time = time.time() - start
start=time.time()
knn_predictions = knn.predict(x_test)
knn_predict_time = time.time() - start
print("Perceptron training time: ", perceptron_train_time, " seconds")
print("KNN training time: ", knn_train_time, " seconds")
print("Perceptron prediction time: ", perceptron_predict_time, " seconds")
print("KNN prediction time: ", knn_predict_time, " seconds")

# SCALING SENSITIVITY
scaler = StandardScaler()
x_train_scaled = scaler.fit_transform(x_train)
x_test_scaled = scaler.transform(x_test)

perceptron.fit(x_train_scaled,y_train)
knn.fit(x_train_scaled, y_train)

acc_p_scaled = accuracy_score(y_test, perceptron.predict(x_test_scaled))
acc_knn_scaled = accuracy_score(y_test, knn.predict(x_test_scaled))

print("Perceptron accuracy with scaling: ", acc_p_scaled * 100, "%")
print("KNN accuracy with scaling: ", acc_knn_scaled * 100, "%")

"""
PART 4
KNN is best for this dataset, since Iris is small, low dimensional, and the KNN model 
consistently achieves 90-100% accuracy. Perceptron'slinear decisions struggle with the 
overlapping data values.

Perceptron would be better ina  very alrge data set which may slow down KNN significantly, and when
the data is linearly seperable, or memory is constrained.

KNN is better when the decision boundry is non linear, and better in small to medium data sets

some of the key trade offs are:
speed: perceptron is faster training and predicting
memory: perceptron only stores weights where knn stores all training data
scaling: perceptron is sensitive to feature scaling, while knn is not as much
stability: perceptron can be sensitive to outliers and noise, while knn is more robust to them
"""


    