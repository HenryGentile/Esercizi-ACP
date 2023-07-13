### uso di multiprocess Pipe

from multiprocessing import Process, Pipe

def parentData(parent):
    parent.send(['Hello'])
    parent.close()
    
def childData(child):
    child.send(['Bye'])
    child.close()

if __name__ == '__main__':
    parent_conn, child_conn = Pipe()
    p1 = Process(target=parentData, args=(parent_conn,))
    p1.start()
    p2 = Process(target=childData, args=(child_conn,))
    p2.start()
    print(parent_conn.recv())
    print(child_conn.recv())   
    p1.join()
    p2.join()