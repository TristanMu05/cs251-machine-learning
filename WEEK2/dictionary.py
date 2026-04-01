# create a dict for contact list with 4 items
# display freds num
# add contact for bobo
# modify cell number for sarah
# remove bob from the lsit
# print entire dictionary

def main():
    contacts = {
        "fred": "555-1234",
        "jane": "555-5678",
        "bob": "555-9012",
        "sarah": "555-3456"
    }
    print(contacts["fred"])
    contacts["bobo"] = "555-7890"
    contacts["sarah"] = "111-1234"
    contacts.pop("bob")
    print(contacts)

if __name__ == "__main__":
    main()

