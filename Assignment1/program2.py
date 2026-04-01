
def main():
    stack = []
    
    print("Enter 'done' when you are finished.")

    while True:
        try:
            num = (input("Enter an integer: "))
            if num.lower() == "done":
                break
            num = int(num)
            stack.append(num)
        except ValueError:
            print("Invalid input. Please enter an integer, or type 'done' when finished")

    print("Numbers in reverse order: ")
    while stack:
        print(stack.pop())


if __name__ == "__main__":
    main()
