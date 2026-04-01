from PIL import Image

def main():
    img = Image.new("RGBA", (50,50), "red")
    img.save("testing.png")

    img = Image.new("RGBA", (500,500), "white")

    with Image.open("testing.png") as paste_img:
        img.paste(paste_img, (100,100))
    img.save("testingpaste.png")


if __name__ == "__main__":
    main()