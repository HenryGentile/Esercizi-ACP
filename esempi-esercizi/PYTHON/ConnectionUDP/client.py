import socket


address = '127.0.0.1'
porto = int(input("porto:"))

#apertura della socket
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

msg = 15

print(f'client: invio del messaggio {msg}')

#invio messaggio
s.sendto(str(msg).encode("utf-8"), (address.encode("utf-8"), porto))

#chiudo la socket
s.close()

