import threading as thd
from threading import Event, Condition
import time
import random

def produci(queue:list, cv:Condition, event:Event):
    with cv:
        while len(queue) == 10:
            print(f'{thd.current_thread().name} coda piena. Attesa di svuotarla')
            cv.wait()
        
        queue.append(round(random.random(), 3))
        print(f'valore prodotto: {queue}')

def consuma(queue:list, cv:Condition, event:Event):

    while True:

        print(f'{thd.current_thread().name} attesa')

        event.wait() #sospende fino a quando l'event non è true

        time.sleep(5)
        lista_copy = queue [:]
        for e in lista_copy:
            print(f'elemento:{e:.3f}')
            queue.remove(e)
        
        event.clear()


        with cv:
            cv.notify_all()
                

if __name__ == "__main__":

    CVlock = thd.Lock()
    cv_produttori = thd.Condition(lock=CVlock)

    event = thd.Event()

    produttori = [] #lista di thread

    queue = [] #lista generata

    #creiamo i thread
    svuotatore = thd.Thread(target=consuma, name="Svuotatore", args=(queue,cv_produttori,event))
    svuotatore.start()


    time.sleep(1)

    for i in range(0,20):
        produttori.append(thd.Thread(target=produci, name=f'Produttore {i}',args=(queue,cv_produttori,event)))
        produttori[i].start()

    for i in range(0,20):
        produttori[i].join()

        if (i % 10) == 0:
            event.set()