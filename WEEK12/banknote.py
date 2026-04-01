import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # 0=all, 1=info, 2=warnings, 3=errors only
import tensorflow as tf
from tensorflow import keras
import numpy as np
import csv
import random

# MY DATA
with open("banknotes.csv") as f: 
    reader = csv.DictReader(f)
    data = list(reader)
    
x = np.array([[float(row["variance"]), float(row["skewness"]), float(row["curtosis"]),
               float(row["entropy"])] for row in data])
y = np.array([int(row["class"]) for row in data])

split = int(0.8 * len(x))
x_train, x_test = x[:split], x[split:]
y_train, y_test = y[:split], y[split:]

# DEFINE THE MODEL
model = keras.Sequential([
    keras.layers.Dense(6, activation='relu', input_shape=(4,)),
    keras.layers.Dense(1, activation='sigmoid')
])

# COMPILE - PICK OPTIMIZER, LOSS, AND METRICS
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# TRAIN
model.fit(x_train, y_train, epochs=40, verbose=1, validation_data=(x_test, y_test))

# EVALUATE
loss, acc = model.evaluate(x_test, y_test, verbose=0)
print(f"Loss: {loss:.4f}, Accuracy: {acc:.4f}")
print(model.predict(x_test[:20]))
