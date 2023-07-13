import socket

host = '127.0.0.1'
porto = 3000 #es. di un porto di default
BUFFERDATI = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((host, porto))


#loop classico di ascolti su una socket
while True:

    print(f'Server in attesa sul porto {porto}')

    s.listen()

    conn, addr = s.accept() #(socket, indirizzo richiedente)

    valore = conn.recv(BUFFERDATI) #egge il valore inviato. Bloccante

    print(f'Valore : {valore.decode("utf-8")}')

    conn.close()