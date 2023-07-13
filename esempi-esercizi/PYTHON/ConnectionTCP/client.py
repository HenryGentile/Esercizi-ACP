import socket

address = '127.0.0.1'
porto = 3000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

msg = "Ciao Server, come stai"

print(f'client : invio del messaggio  " {msg}"')

s.connect((address, porto))

s.send(msg.encode("utf-8"))

s.close()