def commonend(a, b):
    if a[0] == b[0] or a[-1] == b[-1]:
        print("True")
    else:
        print("False")
def main():
    commonend([1,2,3,4,5], [1,2,3,4,5])
    commonend([1,2,3,4,5], [1,2,3,4,6])
    commonend([1,2,3,4,5], [2,2,3,4,4])

if __name__ == "__main__":
    main()
