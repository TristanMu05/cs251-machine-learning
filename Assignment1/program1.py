
from collections import deque

def main():
    stack = deque()

    count = int(input("Enter the number of elements: "))
    
    for i in range(count):
        stack.append(int(input("Enter an element: ")))
    
    print("Numbers in reverse order: ")
    while stack:
        print(stack.pop())
    

if __name__ == "__main__":
    main()