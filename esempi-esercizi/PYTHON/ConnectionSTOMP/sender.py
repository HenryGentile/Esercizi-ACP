import stomp
import random
import time

#settiamo un collegamento con activeMQ
connection = stomp.Connection([('127.0.0.1','61613')])
connection.connect(wait=True)

#inviamo i nostri messaggi
for index in range(0,5):

    msg = round(random.random() * 100, 3)
    print(f'SENDER: messaggio inviato alla coda con valore di {msg}')
    connection.send(destination= '/queue/myQueueTesting', body= str(msg))
    time.sleep(1)

#chiudiamo la connessione una volta termianto
connection.disconnect()
