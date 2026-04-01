from PIL import Image

def main():
    cell_size = 500
    grid_size = 10
    padding = 10

    image_height = grid_size * (padding + cell_size)
    image_width = grid_size * (padding + cell_size)

    output_file = "output.png"

    canvas = Image.new("RGBA", (image_height, image_width), "white")

    dog = Image.open("dog.png").resize((cell_size, cell_size))
    cat = Image.open("cat.png").resize((cell_size, cell_size))

    for row in range(grid_size):
        dogs = grid_size-row
        for col in range(grid_size):
            x = col * cell_size + padding
            y = row * cell_size + padding
            if dogs > 0:
                canvas.paste(dog, (x,y))
                dogs-=1
            else:
                canvas.paste(cat, (x,y))
            
    
    canvas.save(output_file)

if __name__ == "__main__":
    main()