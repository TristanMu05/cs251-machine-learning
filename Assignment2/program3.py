from PIL import Image
import sys

def main():
    output_file = "output.png"
    with open("input.txt", "r") as f:
        lines = [line.strip() for line in f if line.strip()]

    cell_size = 500
    padding = 10

    rows = len(lines)
    cols = len(lines[0])

    image_width  = cols * cell_size + (cols + 1) * padding
    image_height = rows * cell_size + (rows + 1) * padding

    canvas = Image.new("RGBA", (image_width, image_height), "white")

    for row in range(rows):
        for col in range(cols):
            x = padding + col * (cell_size + padding)
            y = padding + row * (cell_size + padding)

            char = lines[row][col]
            if char == "1":
                color = "red"
            elif char == "2":
                color = "green"
            else:
                color = "blue"

            tile = Image.new("RGBA", (cell_size, cell_size), color)
            canvas.paste(tile, (x, y))

    
    canvas.save(output_file)

if __name__ == "__main__":
    main()