from PIL import Image

def output_image():
    cell_size = 500
    padding = 5
    grid_size = 5

    image_height = grid_size * (cell_size + padding)
    image_width = grid_size * (cell_size + padding)

    output_file = "grid.png"

    canvas = Image.new("RGBA", (image_width, image_height), "white")

    cat = Image.open("cat.png").resize((cell_size,cell_size))
    dog = Image.open("dog.png").resize((cell_size,cell_size))

    for row in range(grid_size):
        for col in range(grid_size):
            x = col * cell_size + padding
            y = row * cell_size + padding
            if (row + col) % 2 == 0:
                canvas.paste(cat, (x,y))
            else:
                canvas.paste(dog, (x,y))  
    canvas.save(output_file)

if __name__ == "__main__":
    output_image()

