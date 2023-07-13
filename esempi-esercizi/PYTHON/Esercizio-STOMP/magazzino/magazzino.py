import stomp
import threading as thd
from threading import Lock
import time


#classe coda implementata con un meccanismo di gestione della mutua esclusione
class Buffer(object):

    def __init__(self, s):
        self.size = s
        self.testa = 0
        self.coda = 0
        self.elem = 0
        self.buffer = [None] * self.size
        self.lock = thd.Lock()
        self.CV_prod = thd.Condition(self.lock)
        self.CV_cons = thd.Condition(self.lock)


    def is_full(self):
        if self.elem == self.size:
            return True
        return False
    
    def is_empty(self):
        if self.elem == 0:
            return True
        return False
    
    def produci(self, valore):

        with self.CV_prod:

            while self.is_full():
                print('buffer pieno. Attesa...')
                self.CV_prod.wait()

            time.sleep(1)

            self.buffer[self.testa % self.size] = valore

            print(f'id {self.buffer[self.testa % self.size]} depositato con successo')
            self.testa += 1
            self.elem += 1

            self.CV_cons.notify()

    def consuma(self):

        with self.CV_cons:

            while self.is_empty():
                print('buffer vuoto. Attesa...')
                self.CV_cons.wait()


            time.sleep(1)

            valore = self.buffer[self.coda % self.size]
            print(f'id {valore} prelevato con successo')

            self.coda += 1
            self.elem = self.elem - 1

            self.CV_prod.notify()


            return valore


#funzione eseguita dal thread richiamato
def analisiMessaggio(msg, coda:Buffer, conn:stomp.Connection):

    richiesta = str(msg.body).split('-')

    if richiesta[0] == 'deposita':

        print(f'{thd.current_thread().name}: Richiesta di {richiesta} ricevuta. procedere...')
        id = int(richiesta[1])
        coda.produci(id)

    elif richiesta[0] == 'preleva':

        print(f'{thd.current_thread().name}: Richiesta di {richiesta} ricevuta. procedere...')
        valore = coda.consuma()

        conn.send('/queue/Risposta', body=str(valore))
    else:
        print(f'{thd.current_thread().name}: {richiesta} richiesta non valida')

    

#classe myListener per la ricezione asincrona
class MyListener(stomp.ConnectionListener):

    def __init__(self, c, b):
        self.conn = c
        self.buffer = b
        self.contatore = 1

    def on_message(self, frame):
        print('Listener: Messaggio ottenuto. avvio thread')

        t = thd.Thread(target=analisiMessaggio, name=f'Thread {self.contatore}', args=(frame,self.buffer,self.conn))
        t.start()

        self.contatore += 1



#inizio del server
if __name__ == "__main__":

    conn = stomp.Connection([('127.0.0.1', '61613')])
    conn.connect(wait=True)

    buffer = Buffer(10)

    conn.set_listener('', MyListener(c=conn, b=buffer))
    conn.subscribe(destination='/queue/Richiesta',id=1, ack="auto")

    print("Server: Settato il listener sulla direzione /queue/Richiesta")

    while True:
        pass