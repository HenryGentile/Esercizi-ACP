import stomp
import random

class MyListener(stomp.ConnectionListener):
    def __init__(self, conn):
        self.conn = conn

    def on_message(self, frame):
        print(f'Listener: Id articolo prelevato={frame.body}')

if __name__ == "__main__":

    N = 10

    conn = stomp.Connection([('127.0.0.1', '61613')])
    conn.connect(wait=True)

    conn.set_listener('clientListener', MyListener(conn))
    conn.subscribe('/queue/Risposta', id=1, ack="auto")

    for index in range(0,N):

        if (int(random.random()*100)) % 2 == 0:
            msg =  'deposita' + '-' +str(int(random.random()*100))
        else:
            msg = 'preleva'

        print(f'Client: Messaggio inviato = {msg}')

        conn.send(destination='/queue/Richiesta', body=msg)

    

    while True:
        pass

    

