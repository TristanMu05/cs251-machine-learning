class Queue():
    def __init__(self):
        self.q = []
    def enqueue(self, item):
        self.q.append(item)
    def dequeue(self):
        return self.q.pop(0)
    def peek(self):
        return self.q[0]
    def is_empty(self):
        if len(self.q) == 0:
            return True
        return False
    def size(self):
        return len(self.q)

def main():
    q = Queue()
    q.enqueue('A')
    q.enqueue('B')
    print(q.dequeue())
    print(q.peek())
    print(q.size())
    print(q.is_empty())


if __name__ == "__main__":
    main()