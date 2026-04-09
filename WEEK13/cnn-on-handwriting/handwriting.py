import sys
import tensorflow as tf

# TASK 1: Use MNIST handwriting dataset
mnist = tf.keras.datasets.mnist

# TASK 2: Prepare data for training and testing: 
(x_train, y_train), (x_test, y_test) = mnist.load_data()
# Use black and white color values
x_train, x_test = x_train / 255.0, x_test / 255.0
y_train = tf.keras.utils.to_categorical(y_train)
y_test = tf.keras.utils.to_categorical(y_test)
x_train = x_train.reshape(
    x_train.shape[0], x_train.shape[1], x_train.shape[2], 1
)
x_test = x_test.reshape(
    x_test.shape[0], x_test.shape[1], x_test.shape[2], 1
)

# TASK 3: CREATE A CONVOLUTIONAL NEURAL NETWORK WITH LAYERS
model = tf.keras.models.Sequential([
    
    # LAYER 1: INPUT NODES
    tf.keras.Input(shape=(28,28,1)),
    

    # LAYER 2: CONVOLUTIONAL LAYER. LEARN 32 FILTERS USING A 3 X 3 KERNEL AND THE RELU ACTIVATION FUNCTION
    tf.keras.layers.Conv2D(32, (3,3), activation="relu"),
    tf.keras.layers.BatchNormalization(),
    # LAYER 3: MAX POOLING LAYER WITH A 2 X 2 POOL SIZE
    tf.keras.layers.MaxPooling2D((2,2)),

    # ADDING A 2ND CONV + POOLING
    tf.keras.layers.Conv2D(64, (3,3), activation="relu"),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.MaxPooling2D((2,2)),
    
    
    # LAYER 4: FLATTEN UNITS INTO A LAYER
    tf.keras.layers.Flatten(),    
    
    
    # LAYER 5: HIDDEN LAYER WITH 128 NODES. ADD A DROPOUT LAYER TO PREVENT OVERFITTING. 
    tf.keras.layers.Dense(128, activation="relu"),
    tf.keras.layers.Dropout(0.25),
    
    
    # LAYER 6: OUTPUT LAYER WITH THE NODES FOR 10 DIGITS. USE SOFTMAX TO PRODUCE A PROBABILITY DISTRIBUTION
    tf.keras.layers.Dense(10, activation="softmax")
    
])

 

# TASK 4: Train neural network
model.compile(
    optimizer="adam",
    loss="categorical_crossentropy",
    metrics=["accuracy"]
)
model.fit(x_train, y_train, epochs=10, batch_size=128)

# TASK 5: Evaluate neural network performance
model.evaluate(x_test,  y_test, verbose=2)

# TASK 6: TO USE A MODEL FOR LATER, SAVDE THE MODEL TO A FILE
# Usage: python3 handwriting.py model.h5
if len(sys.argv) == 2:
    filename = sys.argv[1]
    model.save(filename)
    print(f"Model saved to {filename}.")
