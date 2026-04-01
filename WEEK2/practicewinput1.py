import sys

def read_file():
    infile = open("input.txt", "r")
    lines = infile.readlines()
    infile.close()

    for row in lines:
        for char in row:
            if char == "0":
                print("X", end = "")
            elif char == "1":
                print("Y", end = "")
        print()


def main():
    read_file()

if __name__ == "__main__":
    main()
