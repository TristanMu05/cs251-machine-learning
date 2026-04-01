def main():
    check([1,2,3,2,1])
    check([2,2,3])
    check([4,2,3,4])

def check(array):
    if array[0] == array[-1]:
        print(f"Element 0, {array[0]}, and element {len(array) - 1}, {array[-1]}, are the same")
    else:
        print(f"Element 0, {array[0]},and element {len(array) - 1}, {array[-1]}, are not the same")

if __name__ == "__main__":
    main()
