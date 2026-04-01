from stack import Stack

def main():
    stack = Stack()

    print("Enter 5 integers.")
    for i in range(5):
        while True:
            userinput = input("Enter an integer: ")
            try:
                num = int(userinput)
                stack.push(num)
                break
            except ValueError:
                print("Invalid input.")
    
    print("\nInteger in reverse order:")
    while not stack.is_empty():
        print(stack.pop())

if __name__ == "__main__":
    main()
