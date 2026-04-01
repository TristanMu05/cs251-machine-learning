def main():
    map = {}
    userin = int(input("Enter an int: "))
    if userin == 1:
        map = {
            "test" : 5,
            "next": 2,
        }
    else:
        map = {
            "johndoe" : 5,
            "bobodoe": 2,
            "mamabear": 3,
            "papabear": 4,
            "east": 5,
            "west": 5,
            "north": 6,
            "baby": 7,
        }


    # if johndoe exists add janedoe with same value
    if "johndoe" in map:
        map["janedoe"] = map["johndoe"]

    # remove the key value pair for the bobodoe if it exists
    if "bobodoe" in map:
        map.pop("bobodoe")

    # if both mamabear and papabear exist, append their values together and assign it to a key for babybear
    if "mamabear" in map and "papabear" in map:
        map["babybear"] = map["mamabear"] + map["papabear"]
    
    # if the keys east and west exist and have equal vlaues, remove them both from the map
    if "east" in map and "west" in map and map["east"] == map["west"]:
        map.pop("east")
        map.pop("west")
        
    # if one of either north or south exists but not both, set the other to have the same value in the map
    if "north" in map and "south" not in map:
        map["south"] = map["north"]
    elif "south" in map and "north" not in map:
        map["north"] = map["south"]
    
    print(map)

if __name__ == "__main__":
    main()