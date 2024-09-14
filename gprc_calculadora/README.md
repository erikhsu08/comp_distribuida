## Pra rodar
No terminal ou cmd colocar `python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. calculator.proto`, no path do projeto 

## se der algum problema de warning de versao do proto
compilar e rodar com ` python -W ignore calculator_server.py ` ou ` python -W ignore calculator_client.py `
