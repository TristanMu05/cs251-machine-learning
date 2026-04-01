def main():
    sum = 0.0
    for i in range(10):
        num = float(input("Enter a number: "))
        sum += num
        print("The current sum is", sum)
    print(f"The sum of the 10 numbers is {sum}")


if __name__ == "__main__":
    main()