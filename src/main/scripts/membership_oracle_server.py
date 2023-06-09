import keras
import struct
import socket


def parse_bytes_to_float(bytes):
    return struct.unpack('>d', bytes)[0]


def parse_bytes_to_int(bytes):
    return struct.unpack('>i', bytes)[0]


def parse_bytes(data):
    return [parse_bytes_to_float(data[i * 8:(i + 1) * 8]) for i in range(24)]

# Load the VAE model

print("[INFO] - Initializing Model..")

print()

model = keras.models.load_model('res/binary_model.h5')

print(model.summary())

print()

print("[INFO] - Model initialized.\n")


# print(model.summary())

# Create a socket

HOST = "127.0.0.1"  # Standard loopback interface address (localhost)
PORT = 3826  # Port to listen on (non-privileged ports are > 1023)

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Bind the socket to a port and start listening.

sock.bind((HOST, PORT))

sock.listen()

print(f"[INFO] - Listening on port {PORT}..")

# Accept the first connection

conn, addr = sock.accept()

print("[INFO] - Connection Accepted.")

while True:

    data = conn.recv(1024)

    print("[INFO] - Data Received.")

    parsed_data = parse_bytes(data)

    print(f"[INFO] - Query: f{parsed_data}.")

    # query_result = model.predict(parsed_data)

    # print(f"[INFO] - Query Result: f{parsed_data}")

    # if query_result == 1:
    #     conn.send(b"True")
    # else:
    #     conn.send(b"False")




conn.close()

