import socket

address = '127.0.0.1'
porto = int((input("porto:"))) #casting a input
BUFFER_DATA_MAX = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.bind((address, porto))

valore, addr = s.recvfrom(BUFFER_DATA_MAX)

valore = int(valore.decode("utf-8"))

print(f'Server : Valore riceuto = {valore}')

valore = valore + 35

print(f'Server : Valore incrementato. Nuovo valore = {valore}')

s.close()