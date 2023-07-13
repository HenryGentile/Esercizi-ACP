import socket
from IMagazzino import IMagazzino
import threading as thd
import sys
import random
import time

class MagazzinoProxy(IMagazzino):

    def __init__(self, addr, port):
        self.addr = addr
        self.porto = port
        self.BUFFER_MAX_SIZE = 1024
    
    def deposita(self, articolo, id):

        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((self.addr, self.porto))

        msg = 'deposita-' + articolo + '-' + str(id)

        print(f'{thd.current_thread().name}: Richiesta di deposito {msg}')

        s.send(msg.encode("utf-8"))

        s.close()

        

    def preleva(self, articolo):

        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((self.addr, self.porto))

        msg = 'preleva-' + articolo

        print(f'{thd.current_thread().name}: Richiesta di prelievo {msg}')

        s.send(msg.encode("utf-8"))

        valore = s.recv(self.BUFFER_MAX_SIZE)

        print(f'{thd.current_thread().name}: id prelevato = {valore.decode("utf-8")}')

        s.close()




def creazioneRichiesta(richiesta, magazzino:MagazzinoProxy):

    for index in range(0,3):

        time.sleep(random.random() * 2 + 2) 

        if int(random.random()*100) % 2 == 0:
            articolo = 'laptop'
        else:
            articolo = 'smartphone'

        if richiesta == 'deposita':

            id = int(random.random()*100) + 1
            magazzino.deposita(articolo, id)

        elif richiesta == 'preleva':

            magazzino.preleva(articolo)

        


if __name__ == "__main__":

    try:
        porto = int(sys.argv[1])
    except IndexError:
        print("specificare il porto")

    assert porto != '', 'specificare il porto'

    magazzino = MagazzinoProxy('localhost', porto)

    for index in range (0, 10):

        if index % 2 == 0:

            t = thd.Thread(target=creazioneRichiesta, name=f'Thread A-{index}', args=('deposita',magazzino))
            t.start()

        else:

            t = thd.Thread(target=creazioneRichiesta, name=f'Thread B-{index}', args=('preleva',magazzino))
            t.start()

        
