import stomp
import time

class myListener(stomp.ConnectionListener):

    def __init__(self, conn):
        self.connection = conn

    def on_message(self, frame):
        print(f'LISTENER: Messaggio ricevuto. Valore= {frame.body}')
        valore = float(frame.body)
        if (valore % 1) > 0.5:
            valoreint = int(valore) + 1
        else:
            valoreint = int(valore)
        print(f'LISTENER: Valore arrotondato. Nuovo valore:{valoreint}')

if __name__ == "__main__":

    #settiamo la connessione
    connection = stomp.Connection([('127.0.0.1', '61613')])
    connection.connect(wait=True)

    #settiamo il listener
    Listener = myListener(conn=connection)
    connection.set_listener('listener', Listener)

    #sottoscriviamoco ad una destinazione dove vogliamo ricevere i messaggi(asincrono causa listener)
    connection.subscribe(destination='/queue/myQueueTesting', id=1,ack='auto')

    print('RECEIVER:Listener settato. Attesa di 2 minuti...')

    time.sleep(120)

    connection.disconnect()


