def main():
    firstlast6([6,4,5,3,2,5])
    firstlast6([23,32,432,234,5,52,2,7,5,6])
    firstlast6([1,2,3,4,5,6])

def firstlast6(array):
    if array[0] == 6 or array[-1] == 6:
        print("True")
    else:
        print("False")

if __name__ == "__main__":
    main()
