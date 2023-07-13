import socket
import threading as thd
from threading import Lock
from IMagazzino import IMagazzino
import sys

class Buffer():

    def __init__(self, size):
        self.size = size
        self.buffer = [None] * self.size
        self.head = 0
        self.tail = 0
        self.elem = 0
        lock = thd.Lock()
        self.CV_Prod = thd.Condition(lock)
        self.CV_cons = thd.Condition(lock)



    def is_full(self):
        if self.size == self.elem:
            return True
        else:
            return False
        


    def is_empty(self):
        if self.elem == 0:
            return True
        else:
            return False 
        

    def deposita(self, id):
        
        with self.CV_Prod:

            while(self.is_full()):
                print(f'{thd.current_thread().name}: coda piena. attesa...')
                self.CV_Prod.wait()

            self.buffer[self.head % self.size] = id
            print(f'{thd.current_thread().name}: {self.buffer[self.head % self.size]} depositato con successo')

            self.head += 1
            self.elem += 1

            self.CV_cons.notify()

        


    def preleva(self):
        
        with self.CV_cons:

            while(self.is_empty()):
                print(f'{thd.current_thread().name}: coda vuota. attesa...')
                self.CV_cons.wait()

            valore = self.buffer[self.tail % self.size]
            print(f'{thd.current_thread().name}: {self.buffer[self.tail % self.size]} prelevato con successo')

            self.tail += 1
            self.elem -= 1

            self.CV_Prod.notify()

        with open('Magazzino','w') as fileWriter:
            fileWriter.write(f'ID Prelievo: {valore} \n')
        
        return valore




def funcT(magazzino, socket:socket.socket):

    msg = str(socket.recv(1024).decode('utf-8')).split('-')

    if msg[0] == 'deposita':

        #casting del valore da stringa a int
        valore = int(msg[2])
        magazzino.buffer.get(msg[1]).deposita(valore)

    elif msg[0] == 'preleva':

        valore =  magazzino.buffer.get(msg[1]).preleva()
        socket.send(str(valore).encode('utf-8'))

    socket.close()

    
    

class IMagazzinoSkeleton(IMagazzino):

    def __init__(self, addr, port, bufferL, bufferS):
        self.addr = addr
        self.porto = port
        self.BUFFER_MAX_SIZE = 1024
        self.buffer = {'laptop':bufferL, 'smartphone':bufferS}

    def deposita(self, articolo, id):
        self.buffer.deposita(articolo, id)

    def preleva(self, articolo):
        self.buffer.preleva(articolo)

    def getBUFFMAX(self):
        return self.BUFFER_MAX_SIZE

    def runSkeleton(self):

        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((self.addr, self.porto))

        print(f'Socket in ascolto sul porto {self.porto}')

        s.listen(10)

        while True:
            s1, address = s.accept()
            print(f'Socket: apertura connessione con {address[0]} - {address[1]}. Creazione thread')

            t = thd.Thread(target=funcT, name='Thread esecutore', args=(self, s1))
            t.start()

    


if __name__ == "__main__":

    try:
        porto = int(sys.argv[1])
    except IndexError:
        print("specificare il porto")

    assert porto != '', 'specificare il porto'

    buffer_laptop = Buffer(5)
    buffer_smartphone = Buffer(5)

    magazzinoS = IMagazzinoSkeleton('localhost',porto, buffer_laptop, buffer_smartphone)

    print('Magazzino Skeleton run...')
    magazzinoS.runSkeleton()

    
